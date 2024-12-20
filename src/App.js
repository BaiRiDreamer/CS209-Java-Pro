import React from 'react';
import JavaTopics from './components/JavaTopics';
import UserEngagement from './components/UserEngagement';
import CommonMistakes from './components/CommonMistakes';
import AnswerQuality from './components/AnswerQuality';
import './App.css';

function App() {
  return (
    <div className="App">
      <header className="App-header">
        <h1>Stack Overflow Java Data Analysis</h1>
        <p>Insights and analytics on Java-related Stack Overflow data</p>
      </header>
      <main className="App-content">
        <section className="App-section">
          <h2>Java Topics</h2>
          <JavaTopics />
        </section>
        <section className="App-section">
          <h2>User Engagement</h2>
          <UserEngagement />
        </section>
        <section className="App-section">
          <h2>Common Mistakes</h2>
          <CommonMistakes />
        </section>
        <section className="App-section">
          <h2>Answer Quality</h2>
          <AnswerQuality />
        </section>
      </main>
      <footer className="App-footer">
        <p>&copy; 2024 Stack Overflow Java Analysis. All rights reserved.</p>
      </footer>
    </div>
  );
}

export default App;
