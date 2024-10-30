import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import { AuthProvider } from './components/AuthContext';
import HomePage from './components/HomePage';
import Login from './components/Login';

function App() {
  return (
    <AuthProvider> 
      <Router>
        <div className="container">
        <Routes>
          <Route exact path="/" element={<HomePage />} />
          <Route path="/login" element={<Login />} />
        </Routes>
        </div>
      </Router>
    </AuthProvider>
  );
}

export default App;
