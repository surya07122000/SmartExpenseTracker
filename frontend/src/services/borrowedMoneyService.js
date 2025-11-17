import api from "./api";
 
export const addBorrowedMoney = async (data) => {
  const response = await api.post("/api/borrowed-money/addBorrowedMoney", data);
  return response.data;
};
 
export const updateBorrowedMoney = async (id, data) => {
  const response = await api.put(`/api/borrowed-money/updateBorrowedMoney/${id}`, data);
  return response.data;
};
 
export const deleteBorrowedMoney = async (id) => {
  await api.delete(`/api/borrowed-money/deleteBorrowedMoney/${id}`);
};
 
export const getBorrowedMoneyById = async (id) => {
  const response = await api.get(`/api/borrowed-money/getBorrowedMoneyById/${id}`);
  return response.data;
};
 
export const getAllBorrowedMoneyByUser = async (userId, startDate, endDate) => {
  const response = await api.get(`/api/borrowed-money/getAllBorrowedMoneyByUser/${userId}?startDate=${startDate}&endDate=${endDate}`);
  return response.data;
};