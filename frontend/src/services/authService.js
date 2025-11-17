import api from "./api";



export const loginUser = async (email, password) => {
    const response = await api.post("/api/users/signin", { email, password });
    return response.data; // { email, jwt }
};

export const registerUser = async (userData) => {
    const response = await api.post("/api/users/createUser", userData);
    return response.data; // { id, name, email, phoneNumber, role }
};

export const logoutUser = async () => {
    const response = await api.get("/api/users/signout");
    return response.data;
};

export const getUserById = async (id) => {
    const response = await api.get(`/api/users/getUserId/${id}`);
    return response.data;
};

export const getAllUsers = async () => {
    const response = await api.get("/api/users/getAllUsers");
    return response.data; // returns list of UserResponse objects
};


export const updateUser = async (userData) => {
  const response = await api.put(`/api/users/updateUser/${userData.id}`, userData);
  return response.data;
};

export const getProfileData = async (email) => {
    const response = await api.get(`/api/users/profile?email=${email}`);
    return response.data; // { id, name, email, phoneNumber }
};


export const forgotPassword = async (email, newPassword) => {
    const response = await api.put("/api/users/forgot-password", { email,newPassword });
    return response.data;
};

