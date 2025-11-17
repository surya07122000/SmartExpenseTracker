import api from "./api";
 
export const addIncome = async (incomeData) => {
  const response = await api.post("/api/income/addIncome", incomeData);
  return response.data;
};
 
export const updateIncome = async (id, incomeData) => {
  const response = await api.put(`/api/income/updateIncome/${id}`, incomeData);
  return response.data;
};
 
export const deleteIncome = async (id) => {
  await api.delete(`/api/income/deleteIncome/${id}`);
};
 
export const getIncomeById = async (id) => {
  const response = await api.get(`/api/income/getIncomeById/${id}`);
  return response.data;
};
 
export const getAllIncomeByUser = async (userId,  startDate, endDate) => {
  const response = await api.get(`/api/income/getAllIncomeByUser/${userId}?startDate=${startDate}&endDate=${endDate}`);
  return response.data;
};