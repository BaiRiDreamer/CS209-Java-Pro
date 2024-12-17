import React, { useEffect, useState } from 'react';
import ReactECharts from 'echarts-for-react';
import { fetchJavaTopics } from '../api';

const JavaTopics = () => {
  const [data, setData] = useState([]); // 存储后端返回的数据
  const [n, setN] = useState(5); // 默认展示5条数据

  // 监听N的变化，动态获取数据
  useEffect(() => {
    fetchJavaTopics(n).then(response => setData(response.data));
  }, [n]);

  // ECharts 配置
  const options = {
    title: { text: `Top ${n} Java Topics` },
    xAxis: { type: 'category', data: data.map(item => item.topic) },
    yAxis: { type: 'value', name: 'Count' },
    series: [{ type: 'bar', data: data.map(item => item.count) }],
  };

  return (
    <div>
      <h2>Most Asked Java Topics</h2>
      
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
            <th>Count</th>
          </tr>
        </thead>
        <tbody>
          {data.map((item, index) => (
            <tr key={index}>
              <td>{index + 1}</td>
              <td>{item.topic}</td>
              <td>{item.count}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default JavaTopics;
