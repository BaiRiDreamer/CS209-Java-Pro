import React, { useEffect, useState } from 'react';
import ReactECharts from 'echarts-for-react';
import { fetchCommonMistakes } from '../api';

const CommonMistakes = () => {
  const [data, setData] = useState([]);
  const [n, setN] = useState(5); // 默认展示5条错误

  useEffect(() => {
    fetchCommonMistakes(n).then((response) => setData(response.data));
  }, [n]);

  const options = {
    title: { text: `Top ${n} Common Mistakes` },
    series: [
      {
        type: 'pie',
        data: data.map((item) => ({ name: item.error, value: item.count })),
      },
    ],
  };

  return (
    <div>
      <h2>Common Java Mistakes</h2>
      <label htmlFor="n-select">Select N:</label>
      <select id="n-select" value={n} onChange={(e) => setN(Number(e.target.value))}>
        <option value={5}>5</option>
        <option value={10}>10</option>
        <option value={15}>15</option>
      </select>
      <ReactECharts option={options} style={{ height: '400px', marginTop: '20px' }} />
    </div>
  );
};

export default CommonMistakes;
