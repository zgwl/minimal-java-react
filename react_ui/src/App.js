import React, { useEffect, useState } from "react";
import "./App.css";
import Example from "./Example";

const API_URL = "https://minimal-java-api.fly.dev/api/users";

function App() {
  const [users, setUsers] = useState([]);
  const [userId, setUserId] = useState("");
  const [userName, setUserName] = useState("");
  const [userEmail, setUserEmail] = useState("");

  useEffect(() => {
    fetch(API_URL)
      .then((response) => response.json())
      .then((data) => setUsers(data))
      .catch((error) => console.error("Error:", error));
  }, []);

  const handleCreate = () => {
    fetch(API_URL, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ name: userName, email: userEmail }),
    })
      .then((response) => response.json())
      .then((newUser) => {
        setUsers([...users, newUser]);
        setUserName("");
        setUserEmail("");
      })
      .catch((error) => console.error("Error:", error));
  };

  const handleUpdate = () => {
    fetch(`${API_URL}/${userId}`, {
      method: "PUT",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ name: userName, email: userEmail }),
    })
      .then((response) => response.json())
      .then((updatedUser) => {
        const updatedUsers = users.map((user) =>
          user.id === updatedUser.id ? updatedUser : user
        );
        setUsers(updatedUsers);
      })
      .catch((error) => console.error("Error:", error));
  };

  const handleDelete = (id) => {
    fetch(`${API_URL}/${id}`, {
      method: "DELETE",
    })
      .then(() => {
        const filteredUsers = users.filter((user) => user.id !== id);
        setUsers(filteredUsers);
      })
      .catch((error) => console.error("Error:", error));
  };

  return (
    <div className="App">
      <h1>User Management</h1>
      <input
        value={userId}
        onChange={(e) => setUserId(e.target.value)}
        placeholder="User ID"
      />
      <input
        value={userName}
        onChange={(e) => setUserName(e.target.value)}
        placeholder="Name"
      />
      <input
        value={userEmail}
        onChange={(e) => setUserEmail(e.target.value)}
        placeholder="Email"
      />
      <button onClick={handleCreate}>Create User</button>
      <button onClick={handleUpdate}>Update User</button>
      <ul>
        {users.map((user) => (
          <li key={user.id}>
            {user.name} ({user.email})
            <button onClick={() => handleDelete(user.id)}>Delete</button>
          </li>
        ))}
      </ul>
      <div>
        <h1>React Example</h1>
        <Example />
      </div>
    </div>
  );
}

export default App;
