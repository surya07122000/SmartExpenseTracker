import { useEffect, useState } from "react";
import { getAllIncomeByUser } from "../services/incomeService";
import { getAllExpensesByUser } from "../services/expenseService";
import { getDashboardSummary } from "../services/dashboardService";
import { getAllBorrowedMoneyByUser } from "../services/borrowedMoneyService";
import SummaryCards from "../components/dashboard/SummaryCards";
import TransactionTabs from "../components/dashboard/TransactionTabs";
import AddTransactionModal from "../components/dashboard/AddTransactionModal";
import Navbar from "../components/common/Navbar";
import { toast } from "react-toastify";
import { useNavigate } from "react-router-dom";

const DashboardPage = () => {
  const [summary, setSummary] = useState({
    totalIncome: 0,
    totalExpense: 0,
    totalBorrowed: 0,
    netBalance: 0,
  });

  const [transactions, setTransactions] = useState({
    income: [],
    expense: [],
    borrowed: [],
  });

  const [showModal, setShowModal] = useState(false);
  const [activeTab, setActiveTab] = useState("income");

  // ✅ Persist dates in localStorage
  const [startDate, setStartDate] = useState(localStorage.getItem("startDate") || "");
  const [endDate, setEndDate] = useState(localStorage.getItem("endDate") || "");

  const userId = localStorage.getItem("userId");
  const navigate = useNavigate();

  // ✅ Save dates to localStorage whenever they change
  useEffect(() => {
    if (startDate) localStorage.setItem("startDate", startDate);
    if (endDate) localStorage.setItem("endDate", endDate);
  }, [startDate, endDate]);

  // ✅ Reusable function to load dashboard
  const loadDashboard = async () => {
    try {
      const dashboardData = await getDashboardSummary(userId, startDate, endDate);
      setSummary(dashboardData);

      const [incomeData, expenseData, borrowedData] = await Promise.all([
        getAllIncomeByUser(userId, startDate, endDate),
        getAllExpensesByUser(userId, startDate, endDate),
        getAllBorrowedMoneyByUser(userId, startDate, endDate),
      ]);

      setTransactions({
        income: incomeData,
        expense: expenseData,
        borrowed: borrowedData,
      });
    } catch (error) {
      console.error("Error loading dashboard:", error);
    }
  };

  // ✅ Load dashboard when dates change
  useEffect(() => {
    if (!userId || !startDate || !endDate) return;

    if (new Date(endDate) < new Date(startDate)) {
      toast.error("End date must be after start date!");
      return;
    }

    loadDashboard();
  }, [userId, startDate, endDate]);

  return (
    <>
      <Navbar />
      <div className="container mt-4">
        {/* 🔹 Date Range Filter */}
        <div className="d-flex gap-3 mb-3">
          <input
            type="date"
            value={startDate}
            onChange={(e) => setStartDate(e.target.value)}
            className="form-control"
          />
          <input
            type="date"
            value={endDate}
            onChange={(e) => setEndDate(e.target.value)}
            className="form-control"
          />
        </div>

        <div className="d-flex justify-content-between align-items-center mb-4">
          <h3>Dashboard</h3>
          <button
            className="btn btn-primary"
            onClick={() => setShowModal(true)}
          >
            + Add Transaction
          </button>

          <button
            className="btn btn-info"
            onClick={() => navigate("/reports")}
          >
            View Report
          </button>
        </div>

        {/* 🔹 Summary Cards */}
        <div className="row">
          <SummaryCards title="Total Income" value={summary.totalIncome} color="success" />
          <SummaryCards title="Total Expense" value={summary.totalExpense} color="danger" />
          <SummaryCards title="Borrowed Money" value={summary.totalBorrowed} color="warning" />
          <SummaryCards title="Net Balance" value={summary.netBalance} color="info" />
        </div>

        {/* 🔹 Transaction Tabs */}
        <TransactionTabs

          activeTab={activeTab}
          setActiveTab={setActiveTab}
          transactions={transactions}
          setTransactions={setTransactions}
          userId={userId}
          onRefresh={loadDashboard}

        />

        {/* 🔹 Add Transaction Modal */}
        <AddTransactionModal
          show={showModal}
          onClose={() => setShowModal(false)}
          userId={userId}
          onRefresh={loadDashboard}
        />
      </div>
    </>
  );
};

export default DashboardPage;