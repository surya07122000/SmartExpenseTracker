import { useEffect, useState } from "react";
import { getAllIncomeByUser } from "../services/incomeService";
import { getAllExpensesByUser } from "../services/expenseService";
import { getAllBorrowedMoneyByUser } from "../services/borrowedMoneyService";
import { Chart as ChartJS, ArcElement, Tooltip, Legend, CategoryScale, LinearScale, BarElement, } from "chart.js";
ChartJS.register(ArcElement, Tooltip, Legend, CategoryScale, LinearScale, BarElement);
import Navbar from "../components/common/Navbar";
import { Pie, Bar } from "react-chartjs-2";
import { CSVLink } from "react-csv";
import { toast } from "react-toastify";

const ReportsPage = () => {
    const [income, setIncome] = useState([]);
    const [expense, setExpense] = useState([]);
    const [borrowed, setBorrowed] = useState([]);
    const [startDate, setStartDate] = useState("");
    const [endDate, setEndDate] = useState("");

    const userId = localStorage.getItem("userId");

    useEffect(() => {
        if (!userId || !startDate || !endDate) return;

        if (new Date(endDate) < new Date(startDate)) {
            toast.error("End date must be after start date!");
            return;
        }

        const loadReportData = async () => {
            try {
                const [incomeData, expenseData, borrowedData] = await Promise.all([
                    getAllIncomeByUser(userId, startDate, endDate),
                    getAllExpensesByUser(userId, startDate, endDate),
                    getAllBorrowedMoneyByUser(userId, startDate, endDate),
                ]);
                setIncome(incomeData);
                setExpense(expenseData);
                setBorrowed(borrowedData);
            } catch (error) {
                console.error("Error loading report:", error);
            }
        };

        loadReportData();
    }, [userId, startDate, endDate]);

    const totalIncome = income.reduce((sum, item) => sum + item.amount, 0);
    const totalExpense = expense.reduce((sum, item) => sum + item.amount, 0);
    const totalBorrowed = borrowed.reduce((sum, item) => sum + item.amount, 0);

    const pieData = {
        labels: ["Income", "Expense", "Borrowed"],
        datasets: [
            {
                data: [totalIncome, totalExpense, totalBorrowed],
                backgroundColor: ["#28a745", "#dc3545", "#ffc107"],
            },
        ],
    };

    const barData = {
        labels: ["Income", "Expense"],
        datasets: [
            {
                label: "Comparison",
                data: [totalIncome, totalExpense],
                backgroundColor: ["#28a745", "#dc3545"],
            },
        ],
    };

    return (
        <>
            <Navbar />
            <div className="container mt-4">
                <h3 className="mb-4">Report</h3>

                {/* Date Range Filter */}
                <div className="d-flex gap-3 mb-4">
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

                {/* Charts */}
                <div className="row mb-4">
                    <div className="col-md-6">
                        <h5 className="text-center">Income vs Expense</h5>
                        <Bar data={barData} />
                    </div>
                    <div className="col-md-6">
                        <h5 className="text-center">Distribution</h5>
                        <div style={{ width: "550px", height: "250px", margin: "0 auto" }}>
                        <Pie data={pieData}

                            options={{
                                responsive: true,
                                maintainAspectRatio: false,
                            }}

                        />
                        </div>
                    </div>
                </div>

                {/* Download Buttons */}
                <div className="d-flex gap-3 mb-4">
                    <CSVLink
                        data={[...income, ...expense, ...borrowed]}
                        filename={`report_${startDate}_${endDate}.csv`}
                        className="btn btn-success"
                    >
                        Download Report (CSV)
                    </CSVLink>
                </div>
            </div>
        </>
    );
};

export default ReportsPage;