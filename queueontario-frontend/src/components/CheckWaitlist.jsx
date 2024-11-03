import React, { useEffect, useState } from "react";
import { useAuth } from './AuthContext'
import Timer from "./Timer"
import Header from "./Header"
import Footer from "./Footer"
import '../styles/Timer.css'

function CheckWaitlist() {

    const { user } = useAuth()
    const [waitlistTimers, setWaitlistTimers] = useState([])

    useEffect(() => {
        //if(!user) return           //commented out for iteration 1
        const id = "6723fadaab42794e99635d61" //user.id      // "6723fadaab42794e99635d61"  <- for iteration 1

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

        //update every 2 minutes
        const intervalID = setInterval(() => {
            getTimers(id)
        }, 2 * 60 * 1000);
        return () => clearInterval(intervalID);
    }, [])

    //handle timer completion - remove from waitlist after 10 minutes
    const handleComplete = (id) => {
        setTimeout(() => {
            setWaitlistTimers(p => p.filter(t => t.id !== id))
        }, 10 * 60 * 1000)      //10 minutes
    }

    return (
        <div className="container">
            <div className="header">
                <Header />
            </div>
            <div className="inner-container">
                <h1>Waitlist</h1>
                <div className="waitlist-container">
                    {waitlistTimers.map((timer, index) => (
                        <div key={timer.waitlistId} className={`timer-container ${timer.waitlistId % 2 === 0 ? 'light-green' : 'green'}`}>
                            <h2>{timer.location}</h2>
                            <Timer estimatedWaitTime={timer.estimatedWaitTime} waitlistersAhead={timer.waitlistersAhead} onComplete={() => handleComplete(timer.id)} />
                        </div>
                    ))}
                </div>
            </div>
            <div className="footer">
                <Footer />
            </div>
        </div>
    )
}

export default CheckWaitlist