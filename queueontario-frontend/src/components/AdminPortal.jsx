import React, { useState, useEffect } from 'react';
import { useNavigate, Navigate } from 'react-router-dom';
import Header from './Header';
import Footer from "./Footer";
import '../styles/Admin.css';

const AdminPortal = () => {
    const user = localStorage.getItem('userInfo')
    const userInfo = JSON.parse(user)
    const nav = useNavigate()

    if (!user) {
        return <Navigate to="/login" />
    }

    if (userInfo.roles[0] !== 'ROLE_ADMIN') {
        return <div className="admin-wrapper">
            <Header />
            <div className="admin-container">
                You do not have permission to access this page.
            </div>
            <Footer />
        </div>
    }

    const handleUWClick = () => {
        
        nav('/admin/update-waitlist')
    }

    const handleOCClick = () => {
        
        nav('/admin/update-status')
    }

    const handleULClick = () => {
        
        nav('/admin/update-location')
    }

    return (
        <div className="admin-wrapper">
            <Header />
            <main>
                <div className="admin-container">
                <div>
                    <h1>Welcome to the Admin Portal</h1>
                </div>
                    <button className='rsiBtn'
                        type="button"
                        onClick={() => handleUWClick()}>
                        Update Waitlist
                    </button>

                    <button className='rsiBtn'
                        type="button"
                        onClick={() => handleOCClick()}>
                        Open/Close Waitlist
                    </button>

                    <button className='rsiBtn'
                        type="button"
                        onClick={() => handleULClick()}>
                        Update Location
                    </button>
                </div>
            </main>
            <Footer />
        </div>
    )

}

export default AdminPortal