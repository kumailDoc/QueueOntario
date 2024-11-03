import React, { useEffect, useState } from "react";

function Timer({estimatedWaitTime, waitlistersAhead: initial, onComplete}){
    
    const [timeLeft, setTimeLeft] = useState(0)                 
    const [numOfPeople, setNumOfPeople] = useState(initial)     //number of people in waitlist ahead of user

    useEffect(() => {
        const totalTime = estimatedWaitTime * numOfPeople * 60      //time in seconds
        setTimeLeft(totalTime)
    
        //timer function - updates every second
        const timer = setInterval(() => {
            setTimeLeft(prev => {
                if (prev <= 0)
                {
                    clearInterval(timer)
                    onComplete()
                    return 0
                }
                return prev - 1
            })
        }, 1000)

        return () => clearInterval(timer)
    }, [estimatedWaitTime, numOfPeople, onComplete])

    //people count decreases after each estimated WaitTime passes
    useEffect(() => {
        const decreasePeopleCount = setInterval(() => {
            setNumOfPeople(p => {
                if(p > 0) 
                {
                    return p -1
                }
                return 0
            })
        }, estimatedWaitTime * 60 * 1000)       //estimatedWaitTime minute(s) in milliseconds
        return () => clearInterval(decreasePeopleCount)
    }, [estimatedWaitTime])

    //format time in days, hours, minutes, and seconds for display
    const formatTime = (sec) => {
        const days = Math.floor(sec / (3600 * 24))
        const hours = Math.floor((sec % (3600 * 24)) / 3600)
        const minutes = Math.floor((sec % 3600) / 60)
        const secs = sec % 60

        return {days, hours, minutes, secs}
    }

    const {days, hours, minutes, secs} = formatTime(timeLeft)
    const isZero = days === 0 && hours === 0 && minutes === 0 && secs === 0

    return (
        <div>
        {isZero ? (
            <h2>It's Your Turn!</h2>
        ) : (
            <div className="timer-container">
            <h3>Number of People Ahead in Queue:</h3>
            <h3>{numOfPeople}</h3>
            <h3>Estimated Time Left</h3>
            {days > 0 && <span>{days}d :</span>} {hours > 0 && <span>{hours}h :</span>} {minutes > 0 && <span>{minutes}m :</span>} <span>{secs}s</span>
            </div>
        )} 
        </div>
    )
}
export default Timer