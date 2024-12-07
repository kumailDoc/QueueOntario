import React, { useState, useEffect } from 'react';
import { useNavigate, Navigate } from 'react-router-dom';
import Header from './Header';
import Footer from "./Footer";
import '../styles/Admin.css';


const ModPortal = () => {
    const user = localStorage.getItem('userInfo')
    const userInfo = JSON.parse(user)
    const nav = useNavigate()

    if (!user) {
        return <Navigate to="/login" />
    }

    if (userInfo.roles[0] !== 'ROLE_MODERATOR') {

        return <div className="signin-wrapper">
            <Header />
            <div className="signin-container">
                You do not have permission to access this page.</div>
            <Footer />
        </div>
    }
    const handleClick = () => {

        nav('/viewissue')
    }

    return (
        <div className="admin-wrapper">
            <Header />
            <main>
                <div className="admin-container">
                    <div>
                        <h1>Welcome to the Moderator Portal</h1>
                    </div>
                    <button className='rsiBtn'
                        type="button"
                        onClick={() => handleClick()}>
                        Resolve User Issues
                    </button>
                </div>
            </main>
            <Footer />
        </div>

    )



}

export default ModPortal