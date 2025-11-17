import { useEffect, useState } from "react";
import { getAllCategories, addCategory } from "../../services/categoryService";
import { Form, Button } from "react-bootstrap";
import { toast } from "react-toastify";

const CategorySelector = ({ userId, onCategorySelect }) => {
  const [categories, setCategories] = useState([]);
  const [selectedCategory, setSelectedCategory] = useState("");
  const [showCustomInput, setShowCustomInput] = useState(false);
  const [customCategory, setCustomCategory] = useState({ name: "", description: "" });

  useEffect(() => {
    fetchCategories();
  }, []);

  const fetchCategories = async () => {
    try {
      const data = await getAllCategories();
      const numericUserId = Number(userId); // ✅ Fix type mismatch
      const filtered = data.filter(cat => !cat.createdByUserId || cat.createdByUserId === numericUserId);
      setCategories(filtered);
    } catch (error) {
      console.error("Error fetching categories:", error);
      toast.error("Failed to load categories");
    }
  };

  const handleCategoryChange = (e) => {
    const value = e.target.value;
    setSelectedCategory(value);
    if (value === "addNew") {
      setShowCustomInput(true);
    } else {
      setShowCustomInput(false);
      onCategorySelect(value);
    }
  };

  const handleAddCustomCategory = async () => {
    if (!customCategory.name.trim()) {
      toast.error("Category name is required");
      return;
    }
    try {
      const payload = {
        ...customCategory,
        userId,
        createdByUserId: Number(userId) // ✅ Ensure numeric
      };
      const newCategory = await addCategory(payload);
      toast.success("Category added successfully!");
      setCategories([...categories, newCategory]);
      setSelectedCategory(newCategory.id);
      setShowCustomInput(false);
      onCategorySelect(newCategory.id);
    } catch (error) {
      console.error("Error adding category:", error);
      toast.error("Failed to add category");
    }
  };

  return (
    <div>
      <Form.Group className="mb-3">
        <Form.Label>Category</Form.Label>
        <Form.Select value={selectedCategory} onChange={handleCategoryChange}>
          <option value="">Select Category</option>
          {categories.map((cat) => (
            <option key={cat.id} value={cat.id}>
              {cat.name}
            </option>
          ))}
          <option value="addNew">+ Add New Category</option>
        </Form.Select>
      </Form.Group>

      {showCustomInput && (
        <div className="mt-2">
          <Form.Control
            type="text"
            placeholder="Enter category name"
            value={customCategory.name}
            onChange={(e) => setCustomCategory({ ...customCategory, name: e.target.value })}
            className="mb-2"
          />
          <Form.Control
            type="text"
            placeholder="Enter description (optional)"
            value={customCategory.description}
            onChange={(e) => setCustomCategory({ ...customCategory, description: e.target.value })}
            className="mb-2"
          />
          <Button variant="success" onClick={handleAddCustomCategory}>
            Save Category
          </Button>
        </div>
      )}
    </div>
  );
};

export default CategorySelector;