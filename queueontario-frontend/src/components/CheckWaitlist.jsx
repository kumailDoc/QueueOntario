import React, { useEffect, useState } from "react";
import { useNavigate } from 'react-router-dom';
import Timer from "./Timer"
import Header from "./Header"
import Footer from "./Footer"
import '../styles/Timer.css'

function CheckWaitlist() {

    const [userInfo, setUserInfo] = useState(null)
    const [waitlistTimers, setWaitlistTimers] = useState([])
    const navigate = useNavigate();


    useEffect(() => {
        // Retrieve user info from localStorage
        const storedUserInfo = JSON.parse(localStorage.getItem('userInfo'))
        setUserInfo(storedUserInfo)
    }, [])

    useEffect(() => {
        if(!userInfo) return        
        const id = userInfo.id      

        //get data from backend on waitlist timer
        const getTimers = async () => {
            try {
                const response = await fetch(`http://localhost:8080/api/waitlists/user/${id}`)
                const data = await response.json()
                setWaitlistTimers(data)
                console.log(data)
            }
            catch (e) {
                console.error('Error:', e)
            }
        }
        
        getTimers(id).then()

        //update every minute
        const intervalID = setInterval(() => {
            getTimers(id)
        }, 2 * 60 * 1000);
        return () => clearInterval(intervalID);
    }, [userInfo])

    //handle timer completion - remove from waitlist after 10 minutes
    const handleComplete = (id) => {
        setTimeout(() => {
            setWaitlistTimers(p => p.filter(t => t.id !== id))
        }, 10 * 60 * 1000)      //10 minutes
    }

  
   
     // Handle cancel button click
     const handleCancel = async (waitlistId) => {
        try {
            const response = await fetch(`http://localhost:8080/api/waitlists/delete`, {
                method: 'DELETE',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    waitlistId,
                    userId: userInfo.id
                })
            });
            if (response.ok) {
                setWaitlistTimers(timers => timers.filter(timer => timer.waitlistId !== waitlistId));
                console.log("User removed from waitlist");
                // Redirect to homepage
                navigate("/");
            } else {
                console.error("Failed to remove user from waitlist");
            }
        } catch (e) {
            console.error('Error:', e);
        }
    };
    


    return (
        <div className="container">
            <div className="header">
                <Header />
            </div>
            <div className="inner-container">
                <h1>Waitlist</h1>
                <div className="waitlist-container">
                    {waitlistTimers.length > 0 ? (
                        <div>
                            {waitlistTimers
                                .filter(item => item !== null)
                                .map((timer, index) => (
                                    <div key={timer.waitlistId} className={`timer-container ${timer.waitlistId % 2 === 0 ? 'light-green' : 'green'}`}>
                                        <h2>{timer.location}</h2>
                                        <Timer estimatedWaitTime={timer.estimatedWaitTime} waitlistersAhead={timer.waitlistersAhead} onComplete={() => handleComplete(timer.id)} />
                                        <button onClick={() => handleCancel(timer.waitlistId)}>Cancel</button>
                                    </div>
                                ))}
                        </div>
                        
                    ) : (
                        <div>
                            <p> </p>
                        </div>
                    )}
                        
                </div>
            </div>
            <div className="footer">
                <Footer />
            </div>
        </div>
    )
}

export default CheckWaitlist