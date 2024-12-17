import React from 'react';
import JavaTopics from './components/JavaTopics';
import UserEngagement from './components/UserEngagement';
import CommonMistakes from './components/CommonMistakes';
import AnswerQuality from './components/AnswerQuality';
import './App.css';

function App() {
  return (
    <div className="App">
      <h1>Stack Overflow Java Data Analysis</h1>
      <JavaTopics />
      <UserEngagement />
      <CommonMistakes />
      <AnswerQuality />
    </div>
  );
}

export default App;
