import React from 'react';
import Header from './Header';
import Footer from './Footer';
import '../styles/About.css';

const About = () => {
  return (
    <div className="about-page">
      <Header />
      <div className="about-container">
        <h1>About QueueOntario</h1>
        <p>
          QueueOntario is a web application designed to improve the user experience at Service Ontario locations by providing real-time wait times and enabling users to join a waitlist remotely. Built using the Java Spring framework, the app aims to streamline access to government services, making visits more efficient and less time-consuming.
        </p>
        <p>
          The application will be free to use and require users to create an account to access personalized features.
        </p>
      </div>
      <Footer />
    </div>
  );
};

export default About;
