import React, { useEffect, useState } from 'react';
import ReactECharts from 'echarts-for-react';
import { fetchAnswerQuality } from '../api';

const AnswerQuality = () => {
  const [data, setData] = useState([]);
  const [n, setN] = useState(5);

  useEffect(() => {
    fetchAnswerQuality(n).then(response => setData(response.data));
  }, [n]);

  const options = {
    title: { text: `Answer Quality Analysis (Top ${n})` },
    xAxis: { name: 'Reputation', type: 'value' },
    yAxis: { name: 'Votes', type: 'value' },
    series: [
      {
        type: 'scatter',
        data: data.map(item => [item.reputation, item.votes]),
        symbolSize: (data) => Math.sqrt(data[1]) * 2, // 动态大小
      },
    ],
  };

  return (
    <div>
      <h2>Answer Quality Analysis</h2>

      {/* 选择框 */}
      <label htmlFor="n-select">Select N:</label>
      <select id="n-select" value={n} onChange={(e) => setN(Number(e.target.value))}>
        <option value={5}>5</option>
        <option value={10}>10</option>
        <option value={15}>15</option>
      </select>

      {/* 散点图 */}
      <ReactECharts option={options} style={{ height: '400px', marginTop: '20px' }} />

      {/* 表格展示 */}
      <table border="1" cellPadding="8" style={{ marginTop: '20px', width: '100%', textAlign: 'center' }}>
        <thead>
          <tr>
            <th>#</th>
            <th>Reputation</th>
            <th>Votes</th>
            <th>Creation Time</th>
          </tr>
        </thead>
        <tbody>
          {data.map((item, index) => (
            <tr key={index}>
              <td>{index + 1}</td>
              <td>{item.reputation}</td>
              <td>{item.votes}</td>
              <td>{new Date(item.creation_time).toLocaleString()}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default AnswerQuality;
