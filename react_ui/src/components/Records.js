import React, { useEffect, useState } from "react";
import { getDiagnosisRecords } from "../services/chatService";

function Records() {
  const [records, setRecords] = useState([]);

  useEffect(() => {
    const fetchRecords = async () => {
      try {
        const data = await getDiagnosisRecords();
        setRecords(data);
      } catch (error) {
        console.error("Error fetching records:", error);
      }
    };

    fetchRecords();
  }, []);

  return (
    <div className="records-container">
      <h2>Diagnosis Records</h2>
      <div className="records-list">
        {records.map((record) => (
          <div key={record.id} className="record-item">
            <h3>Primary Symptom: {record.primarySymptom}</h3>
            <p>Duration: {record.duration}</p>
            <p>Severity: {record.severity}</p>
            <p>Possible Condition: {record.possibleCondition}</p>
            <p>Confidence: {record.confidence}</p>
            <p>Date: {new Date(record.createdAt).toLocaleString()}</p>
          </div>
        ))}
      </div>
    </div>
  );
}

export default Records;
