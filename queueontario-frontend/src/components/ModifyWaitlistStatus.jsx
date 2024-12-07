import React, { useState, useEffect } from "react";
import { useNavigate } from 'react-router-dom';
import apiClient from "./apiClient";
import '../styles/ModifyWaitlistStatus.css'
import Header from "./Header"
import Footer from "./Footer"

const ModifyWaitlistStatus = () => {
  const [waitlists, setWaitlists] = useState([]);
  const [loading, setLoading] = useState(true);
  const nav = useNavigate()

  useEffect(() => {
    const user = localStorage.getItem('userInfo')
    if (!user) {
      nav('/login')
    }

    const userInfo = JSON.parse(user)
    if (userInfo.roles[0] !== 'ROLE_ADMIN') {
      nav('/')
    }

    // Fetch all waitlists
    const fetchWaitlists = async () => {
      try {
        const response = await apiClient.get(`/api/waitlists/getAll`);
        setWaitlists(response.data);
      } catch (error) {
        console.error("Error fetching waitlists:", error);
      } finally {
        setLoading(false);
      }
    };

    fetchWaitlists();
  }, []);

  const toggleWaitlistStatus = async (waitlistId, currentStatus) => {
    const newStatus = currentStatus === "open" ? "false" : "true";

    try {
      await apiClient.put(`/api/waitlists/admin/update-status`, {
        waitlistId,
        isActive: newStatus,
      });

      // Update local state
      setWaitlists((prevWaitlists) =>
        prevWaitlists.map((waitlist) =>
          waitlist.waitlistId === waitlistId
            ? { ...waitlist, isActive: newStatus }
            : waitlist
        )
      );
    } catch (error) {
      console.error("Error updating waitlist status:", error);
    }
  };

  if (loading) return <div>Loading...</div>;

  return (
    <div class="table-container">

      <Header />
      <h1>Admin Waitlist Management</h1>
      <table>
        <thead>
          <tr>
            <th>Location Name</th>
            <th>Address</th>
            <th>City</th>
            <th>Estimated Wait Time</th>
            <th>Status</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {waitlists.map((waitlist) => (
            <tr key={waitlist.waitlistId}>
              <td>{waitlist.locationName}</td>
              <td>{waitlist.locationAddress}</td>
              <td>{waitlist.locationCity}</td>
              <td>{waitlist.estimatedWaitTime} mins</td>
              <td>{waitlist.isActive === "true" ? "Open" : "Closed"}</td>
              <td>
                <button
                  onClick={() =>
                    toggleWaitlistStatus(waitlist.waitlistId, waitlist.isActive === "true" ? "open" : "closed")
                  }
                >
                  {waitlist.isActive === "true" ? "Close" : "Open"}
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
      <Footer />
    </div>
  );
};

export default ModifyWaitlistStatus;
