import PropTypes from "prop-types";
import React, { useState } from "react";
import { sendMessage } from "../services/chatService";

function Chat({ onDiagnosisComplete }) {
  const [activeTab, setActiveTab] = useState(0);
  const [tabs, setTabs] = useState([
    { id: 0, sessionId: null, messages: [], isComplete: false },
  ]);
  const [input, setInput] = useState("");
  const [isLoading, setIsLoading] = useState(false);

  const handleNewTab = () => {
    const newTab = {
      id: tabs.length,
      sessionId: null,
      messages: [],
      isComplete: false,
    };
    setTabs([...tabs, newTab]);
    setActiveTab(newTab.id);
  };

  const handleTabChange = (tabId) => {
    setActiveTab(tabId);
  };

  const handleSend = async () => {
    if (!input.trim()) return;

    const currentTab = tabs[activeTab];
    const userMessage = { content: input, sender: "user", id: Date.now() };

    // Update current tab messages
    const updatedTabs = [...tabs];
    updatedTabs[activeTab].messages = [...currentTab.messages, userMessage];
    setTabs(updatedTabs);
    setInput("");
    setIsLoading(true);

    try {
      const response = await sendMessage(input, currentTab.sessionId);

      const botMessage = {
        content: response.content,
        sender: "bot",
        id: Date.now() + 1,
      };

      // Update tab with new message and session ID
      const newTabs = [...tabs];
      newTabs[activeTab].messages = [
        ...newTabs[activeTab].messages,
        botMessage,
      ];
      newTabs[activeTab].sessionId = response.sessionId;

      if (response.diagnosisComplete) {
        newTabs[activeTab].isComplete = true;
        onDiagnosisComplete();
      }

      setTabs(newTabs);
    } catch (error) {
      console.error("Error sending message:", error);
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <div className="chat-section">
      <div className="chat-tabs">
        {tabs.map((tab) => (
          <button
            key={tab.id}
            onClick={() => handleTabChange(tab.id)}
            className={`tab ${activeTab === tab.id ? "active" : ""} ${
              tab.isComplete ? "completed" : ""
            }`}
          >
            {`Conversation ${tab.id + 1}${tab.isComplete ? " (Finished)" : ""}`}
          </button>
        ))}
        <button onClick={handleNewTab} className="new-tab">
          + New Chat
        </button>
      </div>

      <div className="chat-container">
        <div className="messages">
          {tabs[activeTab].messages.map((msg) => (
            <div key={msg.id} className={`message ${msg.sender}`}>
              {msg.content}
            </div>
          ))}
          {isLoading && (
            <div className="message bot loading">
              <div className="typing-indicator">
                <span></span>
                <span></span>
                <span></span>
              </div>
            </div>
          )}
        </div>
        <div className="input-area">
          <input
            value={input}
            onChange={(e) => setInput(e.target.value)}
            placeholder="Type your message..."
            onKeyDown={(e) => e.key === "Enter" && handleSend()}
            disabled={isLoading || tabs[activeTab].isComplete}
          />
          <button
            onClick={handleSend}
            disabled={isLoading || tabs[activeTab].isComplete}
          >
            Send
          </button>
        </div>
      </div>
    </div>
  );
}

Chat.propTypes = {
  onDiagnosisComplete: PropTypes.func.isRequired,
};

export default Chat;
