import { useEffect, useState } from "react";
import { addIncome, updateIncome } from "../../services/incomeService";
import { addExpense, updateExpense } from "../../services/expenseService";
import { addBorrowedMoney, updateBorrowedMoney } from "../../services/borrowedMoneyService";
import { Modal, Button, Form } from "react-bootstrap";
import { toast } from "react-toastify";
import CategorySelector from "./CategorySelector";


const AddTransactionModal = ({
  show,
  onClose,
  userId,
  transaction,
  isEditMode,
  activeTab,
  onUpdate,
  onRefresh
}) => {
  const [type, setType] = useState(activeTab || "income");
  const [form, setForm] = useState({});

  // ✅ Utility function for current month last date
  const getLastDateOfCurrentMonth = () => {
    const today = new Date();
    return new Date(today.getFullYear(), today.getMonth() + 1, 0)
      .toISOString()
      .split("T")[0];
  };

  useEffect(() => {
    if (isEditMode && transaction) {
      setType(activeTab);
      setForm(transaction);
    } else {
      setType(activeTab || "income");
      setForm({});
    }
  }, [transaction, isEditMode, activeTab]);

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const userId = localStorage.getItem("userId");
      const payload = { ...form, userId: userId };

      if (!payload.amount || payload.amount <= 0) {
        toast.error("Amount must be greater than zero");
        return;
      }

      // ✅ Validation for borrowed money due date
      if (type === "borrowed") {
        if (payload.dueDate && payload.borrowedDate) {
          const borrowedDateObj = new Date(payload.borrowedDate);
          const dueDateObj = new Date(payload.dueDate);
          if (dueDateObj <= borrowedDateObj) {
            toast.error("Due date must be after borrowed date");
            return;
          }
        }
      }

      if (isEditMode) {
        if (type === "income") {
          await updateIncome(transaction.id, payload);
        } else if (type === "expense") {
          await updateExpense(transaction.id, payload);
        } else if (type === "borrowed") {
          await updateBorrowedMoney(transaction.id, payload);
        }
        toast.success(`${type} updated successfully!`);
        onUpdate({ ...payload, id: transaction.id });
      } else {
        if (type === "income") {
          if (!payload.source || !payload.description || !payload.date) {
            toast.error("Please fill all income fields");
            return;
          }
          await addIncome(payload);
        } else if (type === "expense") {
          if (!payload.title || !payload.date || !payload.categoryId) {
            toast.error("Please fill all expense fields");
            return;
          }
          await addExpense(payload);
        } else if (type === "borrowed") {
          if (!payload.borrowedFrom || !payload.borrowedDate) {
            toast.error("Please fill all borrowed money fields");
            return;
          }
          await addBorrowedMoney(payload);
        }
        toast.success(`${type} added successfully!`);
      }

      if (onRefresh) onRefresh();
      onClose();
    } catch (error) {
      toast.error(error.response?.data?.message || "Operation failed!");
    }
  };

  const renderFields = () => {
    switch (type) {
      case "income":
        return (
          <>
            <Form.Group className="mb-3">
              <Form.Label>Source</Form.Label>
              <Form.Control
                type="text"
                value={form.source || ""}
                onChange={(e) => setForm({ ...form, source: e.target.value })}
              />
            </Form.Group>
            <Form.Group className="mb-3">
              <Form.Label>Amount</Form.Label>
              <Form.Control
                type="number"
                value={form.amount || ""}
                onChange={(e) => setForm({ ...form, amount: e.target.value })}
              />
            </Form.Group>
            <Form.Group className="mb-3">
              <Form.Label>Date</Form.Label>
              <Form.Control
                type="date"
                value={form.date || ""}
                max={getLastDateOfCurrentMonth()} // ✅ Restrict future month dates
                onChange={(e) => setForm({ ...form, date: e.target.value })}
              />
            </Form.Group>
            <Form.Group className="mb-3">
              <Form.Label>Description</Form.Label>
              <Form.Control
                type="text"
                value={form.description || ""}
                onChange={(e) =>
                  setForm({ ...form, description: e.target.value })
                }
              />
            </Form.Group>
          </>
        );
      case "expense":
        return (
          <>
            <Form.Group className="mb-3">
              <Form.Label>Title</Form.Label>
              <Form.Control
                type="text"
                value={form.title || ""}
                onChange={(e) => setForm({ ...form, title: e.target.value })}
              />
            </Form.Group>
            <Form.Group className="mb-3">
              <Form.Label>Amount</Form.Label>
              <Form.Control
                type="number"
                value={form.amount || ""}
                onChange={(e) => setForm({ ...form, amount: e.target.value })}
              />
            </Form.Group>
            <Form.Group className="mb-3">
              <Form.Label>Date</Form.Label>
              <Form.Control
                type="date"
                value={form.date || ""}
                onChange={(e) => setForm({ ...form, date: e.target.value })}
              />
            </Form.Group>
            <CategorySelector
              userId={userId}
              onCategorySelect={(id) => setForm({ ...form, categoryId: id })}
            />
          </>
        );
      case "borrowed":
        return (
          <>
            <Form.Group className="mb-3">
              <Form.Label>Borrowed From</Form.Label>
              <Form.Control
                type="text"
                value={form.borrowedFrom || ""}
                onChange={(e) =>
                  setForm({ ...form, borrowedFrom: e.target.value })
                }
              />
            </Form.Group>
            <Form.Group className="mb-3">
              <Form.Label>Amount</Form.Label>
              <Form.Control
                type="number"
                value={form.amount || ""}
                onChange={(e) => setForm({ ...form, amount: e.target.value })}
              />
            </Form.Group>
            <Form.Group className="mb-3">
              <Form.Label>Borrowed Date</Form.Label>
              <Form.Control
                type="date"
                value={form.borrowedDate || ""}
                max={getLastDateOfCurrentMonth()} // ✅ includes last day of month
                onChange={(e) =>
                  setForm({ ...form, borrowedDate: e.target.value })
                }
              />
            </Form.Group>
            <Form.Group className="mb-3">
              <Form.Label>Due Date</Form.Label>
              <Form.Control
                type="date"
                value={form.dueDate || ""}
                onChange={(e) => setForm({ ...form, dueDate: e.target.value })}
              />
            </Form.Group>
          </>
        );
      default:
        return null;
    }
  };

  return (
    <Modal show={show} onHide={onClose}>
      <Modal.Header closeButton>
        <Modal.Title>
          {isEditMode ? "Edit Transaction" : "Add Transaction"}
        </Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <Form onSubmit={handleSubmit}>
          {!isEditMode && (
            <Form.Group className="mb-3">
              <Form.Label>Type</Form.Label>
              <Form.Select
                value={type}
                onChange={(e) => {
                  setType(e.target.value);
                  setForm({});
                }}
              >
                <option value="income">Income</option>
                <option value="expense">Expense</option>
                <option value="borrowed">Borrowed Money</option>
              </Form.Select>
            </Form.Group>
          )}
          {renderFields()}
          <Button variant="primary" type="submit">
            {isEditMode ? "Update" : "Add"} {type}
          </Button>
        </Form>
      </Modal.Body>
    </Modal>
  );
};

export default AddTransactionModal;
