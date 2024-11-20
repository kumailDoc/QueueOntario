import React, { useEffect, useState } from "react";
import apiClient from "./apiClient";
import Header from "./Header";
import Footer from "./Footer";
import "../styles/UpdateWaitlistAdmin.css"; 

const UpdateWaitlistAdmin = () => {
  const [waitlists, setWaitlists] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");
  const [editingWaitlist, setEditingWaitlist] = useState(null); // Track which waitlist is being edited

  // Fetch waitlists on component mount
  useEffect(() => {
    const fetchWaitlists = async () => {
      try {
        const response = await apiClient.get("/api/waitlists/getAll");
        setWaitlists(response.data || []);
        setLoading(false);
          
      } catch (err) {
        setError("Failed to fetch waitlists.");
        setLoading(false);
      }
    };

    fetchWaitlists();
  }, []);

  // Handle remove user
  const handleRemoveUser = async (waitlistId, userId) => {
    try {
      await apiClient.put("/api/waitlists/admin/update-waitlist", {
        waitlistId,
        removeUserIds: [userId],
      });

      // Update state
      setWaitlists((prevWaitlists) =>
        prevWaitlists.map((waitlist) =>
          waitlist.waitlistId === waitlistId
            ? {
                ...waitlist,
                waitlisters: waitlist.waitlisters.filter((user) => user !== userId),
              }
            : waitlist
        )
      );
      alert("User removed successfully!");
    } catch (error) {
        if(error.response?.status === 401){
            setError("Your session has expired. Please login again");
        }else if(error.response?.status === 403){
            setError("You do not have the required permissions to perform this action.");
        }else{
            setError("Failed to remove user. please try again.");

        }
    }
  };

  // Handle edit average wait time
  const handleEditAverageWaitTime = async (waitlistId) => {
    try {
      const waitlistToEdit = waitlists.find((w) => w.waitlistId === waitlistId);
      await apiClient.put("/api/waitlists/admin/update-waitlist", {
        waitlistId,
        averageWaitTime: waitlistToEdit.estimatedWaitTime,
      });

      setEditingWaitlist(null);
      alert("Average wait time updated successfully!");
    } catch (error) {
        if(error.response?.status === 401){
            setError("Your session has expired. Please login again");
        }else if(error.response?.status === 403){
            setError("You do not have the required permissions to perform this action.");
        }else{
            setError("Failed to update average wait time.");
        }
      //alert("Failed to update average wait time.");
    }
  };

  if (loading) return <p>Loading waitlists...</p>;
  if (error) return <p>{error}</p>;
  

  return (
    <div className="container">
      <Header />
      <div className="main-content">
        <h2>Waitlist Management</h2>
        
        <div className="scrollable-table">
          <table>
            <thead>
              <tr>
                <th>Location Name</th>
                <th>Address</th>
                <th>City</th>
                <th>Estimated Wait Time</th>
                <th>Waitlisters</th>
                
              </tr>
            </thead>
            <tbody>
              {waitlists.map((waitlist) => (
                <tr key={waitlist.waitlistId}>
                  <td>{waitlist.locationName || "Unknown Location"}</td>
                  <td>{waitlist.locationAddress || "N/A"}</td>
                  <td>{waitlist.locationCity || "N/A"}</td>
                  <td>
                    {editingWaitlist === waitlist.waitlistId ? (
                      <>
                        <input
                          type="number"
                          value={waitlist.estimatedWaitTime}
                          onChange={(e) =>
                            setWaitlists((prev) =>
                              prev.map((w) =>
                                w.waitlistId === waitlist.waitlistId
                                  ? { ...w, estimatedWaitTime: e.target.value }
                                  : w
                              )
                            )
                          }
                          style={{ width: "60px" }}
                        />
                        <button onClick={() => handleEditAverageWaitTime(waitlist.waitlistId)}>Save</button>
                        <button onClick={() => setEditingWaitlist(null)}>Cancel</button>
                      </>
                    ) : (
                      <>
                        {waitlist.estimatedWaitTime} minutes{" "}
                        <button onClick={() => setEditingWaitlist(waitlist.waitlistId)}>Edit</button>
                      </>
                    )}
                  </td>
                  <td>
                    {waitlist.waitlisters.length > 0 ? (
                      <ul>
                        {waitlist.waitlisters.map((user) => (
                          <li key={`${waitlist.waitlistId}-${user.userId}`}>
                            {user.username}{" "}
                            <button onClick={() => handleRemoveUser(waitlist.waitlistId, user.userId)}>Remove</button>
                          </li>
                        ))}
                      </ul>
                    ) : (
                      <p>No users on the waitlist.</p>
                    )}
                  </td>
                 
                </tr>
              ))}
            </tbody>
          </table>
        </div>
        
      </div>
      <Footer />
    </div>
  );
};

export default UpdateWaitlistAdmin;

