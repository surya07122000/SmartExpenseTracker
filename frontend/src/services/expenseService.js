import api from "./api";
 
export const addExpense = async (expenseData) => {
  const response = await api.post("/api/expenses/addExpense", expenseData);
  return response.data;
};
 
export const updateExpense = async (id, expenseData) => {
  const response = await api.put(`/api/expenses/updateExpense/${id}`, expenseData);
  return response.data;
};
 
export const deleteExpense = async (id) => {
  await api.delete(`/api/expenses/deleteExpense/${id}`);
};
 
export const getExpenseById = async (id) => {
  const response = await api.get(`/api/expenses/getExpenseById/${id}`);
  return response.data;
};
 
export const getAllExpensesByUser = async (userId, startDate, endDate) => {
  const response = await api.get(`/api/expenses/getAllExpensesByUser/${userId}?startDate=${startDate}&endDate=${endDate}`);
  return response.data;
};