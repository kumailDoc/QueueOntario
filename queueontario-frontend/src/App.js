import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import { AuthProvider } from './components/AuthContext';
import HomePage from './components/HomePage';
import Login from './components/Login';
import Signup from './components/Signup';
import JoinWaitList from './components/Join';
import About from './components/About';
import Report from './components/Report';
import ServiceOntarioList from './components/ServiceOntarioList';


function App() {
  return (
    <AuthProvider> 
      <Router>
        <div className="container">
        <Routes>
          <Route exact path="/" element={<HomePage />} />
          <Route path="/login" element={<Login />} />
          <Route path="/signup" element={<Signup />} />
          <Route path="/join" element={<JoinWaitList />} />
          <Route path="/reportissue" element={<Report />} />
          <Route path="/about" element={<About />} />
          <Route path="/serviceontario" element={<ServiceOntarioList />} /> {/* Add this line */}

        </Routes>
        </div>
      </Router>
    </AuthProvider>
  );
}

export default App;
