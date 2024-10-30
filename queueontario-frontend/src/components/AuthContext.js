import React, { createContext, useContext, useState } from 'react';

const AuthContext = createContext();

export const useAuth = () => useContext(AuthContext);

export const AuthProvider = ({ children }) => {
    const [isLoggedIn, setIsLoggedIn] = useState(false);
    const [user, setUser] = useState(null);
  
    const login = (userData) => {
      console.log('Step before setting user state. User data:', userData); // Log user data before setting user state
      setIsLoggedIn(true);
      setUser(userData);
    };
  
    const logout = () => {
      setIsLoggedIn(false);
      setUser(null);
    };
  
    return (
      <AuthContext.Provider value={{ isLoggedIn, login, logout, user }}>
        {children}
      </AuthContext.Provider>
    );
  };
