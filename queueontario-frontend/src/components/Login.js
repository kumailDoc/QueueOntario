import React, { useState } from 'react';
import Header from './Header';
import Footer from "./Footer";
import '../styles/Login.css';
import logo from '../assets/logo.JPG';

const Login = () => {
  return (
    <div className="signin-wrapper">
      <Header />
      <main>
        <div className="signin-container">
          <img src={logo} className='logo' alt="Logo" />
          <form className="signin-form">
            <h2>Sign in</h2>
            <p>Stay up to date with waiting</p>
            <input 
              type="text" 
              placeholder="Email or username" 
            />
            <input 
              type="password" 
              placeholder="Password" 
            />
            <a href="#" className="forgot-password">Forgot password?</a>
            <button type="submit">Login</button>
            <div className="signup-link">Don't have an account? <a href="/signup">Sign up</a></div>
          </form>
        </div>
      </main>
      <Footer />
    </div>
  );
};

export default Login;