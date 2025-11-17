import api from "./api";

export const getDashboardSummary = async (userId, startDate, endDate) => {
  if (!userId) throw new Error("User ID is required");
  try {
    const response = await api.get(`/api/dashboard/getDashboardSummary/${userId}?startDate=${startDate}&endDate=${endDate}`);
    return response.data;
  } catch (error) {
    console.error("Error fetching dashboard summary:", error);
    return {
      totalIncome: 0,
      totalExpense: 0,
      totalBorrowedMoney: 0,
      netBalance: 0,
    };
  }
};