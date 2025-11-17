import api from "./api";
 
export const addCategory = async (categoryData) => {
  const response = await api.post("/api/categories/addCategory", categoryData);
  return response.data;
};
 
export const updateCategory = async (id, categoryData) => {
  const response = await api.put(`/api/categories/updateCategory/${id}`, categoryData);
  return response.data;
};
 
export const deleteCategory = async (id) => {
  await api.delete(`/api/categories/deleteCategory/${id}`);
};
 
export const getCategoryById = async (id) => {
  const response = await api.get(`/api/categories/getCategoryById/${id}`);
  return response.data;
};
 
export const getAllCategories = async () => {
  const response = await api.get("/api/categories/getAllCategories");
  return response.data;
};
 
export const getCustomCategoryByUser = async (userId) => {
  const response = await api.get(`/api/categories/getCustomCategoryByUser/${userId}`);
  return response.data;
};