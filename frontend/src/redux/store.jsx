const store = configureStore({
    reducer: {
        auth: authReducer,
    },
});
 
export default store;