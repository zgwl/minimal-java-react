import React, { useState } from "react";
import "./App.css";
import Chat from "./components/Chat";
import Login from "./components/Login";
import Records from "./components/Records";

function App() {
  const [isLoggedIn, setIsLoggedIn] = useState(!!localStorage.getItem("token"));
  const [refreshRecords, setRefreshRecords] = useState(0);

  const handleLoginSuccess = () => {
    setIsLoggedIn(true);
  };

  const handleLogout = () => {
    localStorage.removeItem("token");
    setIsLoggedIn(false);
  };

  const handleDiagnosisComplete = () => {
    setRefreshRecords((prev) => prev + 1);
  };

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
