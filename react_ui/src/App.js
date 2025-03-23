import React, { useEffect, useState } from "react";
import "./App.css";
import Chat from "./components/Chat";
import Login from "./components/Login";
import Records from "./components/Records";
import { isAuthenticated, logout } from "./services/authService";

function App() {
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [isLoading, setIsLoading] = useState(true);
  const [refreshRecords, setRefreshRecords] = useState(0);

  useEffect(() => {
    const checkAuth = async () => {
      const authenticated = await isAuthenticated();
      setIsLoggedIn(authenticated);
      setIsLoading(false);
    };

    checkAuth();
  }, []);

  const handleLoginSuccess = () => {
    setIsLoggedIn(true);
  };

  const handleLogout = async () => {
    await logout();
    setIsLoggedIn(false);
  };

  const handleDiagnosisComplete = () => {
    setRefreshRecords((prev) => prev + 1);
  };

  if (isLoading) {
    return <div className="loading">Loading...</div>;
  }

  if (!isLoggedIn) {
    return <Login onLoginSuccess={handleLoginSuccess} />;
  }

  return (
    <div className="App">
      <header>
        <h1>AI Medical Assistant</h1>
        <button onClick={handleLogout}>Logout</button>
      </header>
      <main>
        <div className="container">
          <div className="chat-section">
            <Chat onDiagnosisComplete={handleDiagnosisComplete} />
          </div>
          <div className="records-section">
            <Records key={refreshRecords} />
          </div>
        </div>
      </main>
    </div>
  );
}

export default App;
