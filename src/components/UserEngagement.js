import React, { useEffect, useState } from 'react';
import ReactECharts from 'echarts-for-react';
import { fetchUserEngagement } from '../api';

const UserEngagement = () => {
  const [data, setData] = useState([]);
  const [n, setN] = useState(5);

  useEffect(() => {
    fetchUserEngagement(n).then(response => setData(response.data));
  }, [n]);

  const options = {
    title: { text: `Top ${n} Topics by User Engagement` },
    xAxis: { type: 'category', data: data.map(item => item.topic) },
    yAxis: { type: 'value', name: 'Engagement' },
    series: [{ type: 'bar', data: data.map(item => item.engagement) }],
  };

  return (
    <div>
      <h2>User Engagement Analysis</h2>

      {/* 选择框 */}
      <label htmlFor="n-select">Select N:</label>
      <select id="n-select" value={n} onChange={(e) => setN(Number(e.target.value))}>
        <option value={5}>5</option>
        <option value={10}>10</option>
        <option value={15}>15</option>
      </select>

      {/* 图表展示 */}
      <ReactECharts option={options} style={{ height: '400px', marginTop: '20px' }} />

      {/* 表格展示 */}
      <table border="1" cellPadding="8" style={{ marginTop: '20px', width: '100%', textAlign: 'center' }}>
        <thead>
          <tr>
            <th>#</th>
            <th>Topic</th>
            <th>Engagement</th>
          </tr>
        </thead>
        <tbody>
          {data.map((item, index) => (
            <tr key={index}>
              <td>{index + 1}</td>
              <td>{item.topic}</td>
              <td>{item.engagement}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default UserEngagement;
