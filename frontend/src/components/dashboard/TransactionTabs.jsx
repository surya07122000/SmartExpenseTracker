import { useState } from "react";
import { deleteIncome } from "../../services/incomeService";
import { deleteExpense } from "../../services/expenseService";
import { deleteBorrowedMoney } from "../../services/borrowedMoneyService";
import { toast } from "react-toastify";
import AddTransactionModal from "./AddTransactionModal";

const TransactionTabs = ({
  activeTab,
  setActiveTab,
  transactions,
  setTransactions,
  userId,
  onRefresh // ✅ Added refresh callback
}) => {
  const [showModal, setShowModal] = useState(false);
  const [selectedTransaction, setSelectedTransaction] = useState(null);
  const [isEditMode, setIsEditMode] = useState(false);

  const handleEdit = (transaction) => {
    setSelectedTransaction(transaction);
    setIsEditMode(true);
    setShowModal(true);
  };

  const handleAdd = () => {
    setSelectedTransaction(null);
    setIsEditMode(false);
    setShowModal(true);
  };

  const handleDelete = async (id) => {
    const confirmDelete = window.confirm("Are you sure you want to delete this transaction?");
    if (!confirmDelete) return;

    try {
      if (activeTab === "income") {
        await deleteIncome(id);
      } else if (activeTab === "expense") {
        await deleteExpense(id);
      } else if (activeTab === "borrowed") {
        await deleteBorrowedMoney(id);
      }

      toast.success(`${activeTab} deleted successfully!`);

      // ✅ Refresh dashboard summary and transactions
      if (onRefresh) onRefresh();
    } catch (error) {
      toast.error("Failed to delete transaction!");
    }
  };

  const getColumns = () => {
    if (activeTab === "income") return ["Source", "Amount", "Date", "Description"];
    if (activeTab === "expense") return ["Title", "Amount", "Category", "Date"];
    if (activeTab === "borrowed") return ["BorrowedFrom", "Amount", "BorrowedDate", "DueDate"];
    return [];
  };

  return (
    <div className="mt-4">
      {/* Tabs */}
      <ul className="nav nav-tabs mb-3">
        <li className="nav-item">
          <button
            className={`nav-link ${activeTab === "income" ? "active" : ""}`}
            onClick={() => setActiveTab("income")}
          >
            Income
          </button>
        </li>
        <li className="nav-item">
          <button
            className={`nav-link ${activeTab === "expense" ? "active" : ""}`}
            onClick={() => setActiveTab("expense")}
          >
            Expense
          </button>
        </li>
        <li className="nav-item">
          <button
            className={`nav-link ${activeTab === "borrowed" ? "active" : ""}`}
            onClick={() => setActiveTab("borrowed")}
          >
            Borrowed Money
          </button>
        </li>
      </ul>

      {/* Add Button */}
      <div className="d-flex justify-content-end mb-2">
        <button className="btn btn-primary" onClick={handleAdd}>
          Add {activeTab.charAt(0).toUpperCase() + activeTab.slice(1)}
        </button>
      </div>

      {/* Table */}
      <table className="table table-striped mt-3">
        <thead>
          <tr>
            {getColumns().map((col) => (
              <th key={col}>{col}</th>
            ))}
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {transactions[activeTab]?.length > 0 ? (
            transactions[activeTab].map((t) => (
              <tr key={t.id}>
                {activeTab === "borrowed" ? (
                  <>
                    <td>{t.borrowedFrom || "-"}</td>
                    <td>₹ {t.amount}</td>
                    <td>{t.borrowedDate || "-"}</td>
                    <td>{t.dueDate || "-"}</td>
                  </>
                ) : (
                  getColumns().map((col) => (
                    <td key={col}>
                      {col === "Amount"
                        ? `₹ ${t.amount}`
                        : col === "Category"
                        ? t.categoryName || "-"
                        : t[col.toLowerCase()] || "-"}
                    </td>
                  ))
                )}
                <td>
                  <button
                    className="btn btn-warning btn-sm me-2"
                    onClick={() => handleEdit(t)}
                  >
                    Edit
                  </button>
                  <button
                    className="btn btn-danger btn-sm"
                    onClick={() => handleDelete(t.id)}
                  >
                    Delete
                  </button>
                </td>
              </tr>
            ))
          ) : (
            <tr>
              <td colSpan={getColumns().length + 1} className="text-center text-muted">
                No records found
              </td>
            </tr>
          )}
        </tbody>
      </table>

      {/* Modal for Add/Edit */}
      {showModal && (
        <AddTransactionModal
          show={showModal}
          onClose={() => {
            setShowModal(false);
            setIsEditMode(false);
            setSelectedTransaction(null);
          }}
          userId={userId}
          transaction={selectedTransaction}
          isEditMode={isEditMode}
          activeTab={activeTab}
          onRefresh={onRefresh} 
        />
      )}
    </div>
  );
};

export default TransactionTabs;