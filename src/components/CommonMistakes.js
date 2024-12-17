import React, { useEffect, useState } from 'react';
import ReactECharts from 'echarts-for-react';
import { fetchCommonMistakes } from '../api';

const CommonMistakes = () => {
  const [data, setData] = useState([]);
  const [n, setN] = useState(5);

  useEffect(() => {
    fetchCommonMistakes(n).then(response => setData(response.data));
  }, [n]);

  const options = {
    title: { text: `Top ${n} Java Errors and Exceptions` },
    series: [
      {
        type: 'pie',
        data: data.map(item => ({ name: item.error, value: item.count })),
      },
    ],
  };

  return (
    <div>
      <h2>Common Mistakes in Java</h2>

      {/* 选择框 */}
      <label htmlFor="n-select">Select N:</label>
      <select id="n-select" value={n} onChange={(e) => setN(Number(e.target.value))}>
        <option value={5}>5</option>
        <option value={10}>10</option>
        <option value={15}>15</option>
      </select>

      {/* 饼图展示 */}
      <ReactECharts option={options} style={{ height: '400px', marginTop: '20px' }} />

      {/* 表格展示 */}
      <table border="1" cellPadding="8" style={{ marginTop: '20px', width: '100%', textAlign: 'center' }}>
        <thead>
          <tr>
            <th>#</th>
            <th>Error</th>
            <th>Count</th>
          </tr>
        </thead>
        <tbody>
          {data.map((item, index) => (
            <tr key={index}>
              <td>{index + 1}</td>
              <td>{item.error}</td>
              <td>{item.count}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default CommonMistakes;
