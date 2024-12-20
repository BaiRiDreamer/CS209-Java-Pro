import React, { useEffect, useState } from 'react';
import ReactECharts from 'echarts-for-react';
import { fetchTopJavaTopics, fetchSpecificJavaTopics } from '../api'; // 引入两个接口

const JavaTopics = () => {
  const [data, setData] = useState([]); // 存储话题数据
  const [num, setNum] = useState(5); // 默认展示前5个热门话题
  const [specificTopics, setSpecificTopics] = useState(''); // 用户输入的特定话题
  const [mode, setMode] = useState('top'); // 模式切换（top 或 specific）
  const [loading, setLoading] = useState(false); // 数据加载状态

  // 根据模式加载数据
  useEffect(() => {
    const fetchData = async () => {
      setLoading(true); // 开始加载数据
      try {
        if (mode === 'top') {
          // 加载最热门的 Java 话题
          const response = await fetchTopJavaTopics(num);
          setData(Array.isArray(response.data) ? response.data : []); // 确保 data 是数组
        } else if (mode === 'specific' && specificTopics) {
          // 加载特定 Java 话题
          const response = await fetchSpecificJavaTopics(specificTopics.split(','));
          setData(Array.isArray(response.data) ? response.data : []); // 确保 data 是数组
        }
      } catch (error) {
        console.error('Error fetching data:', error);
        setData([]); // 出错时设置 data 为空数组
      } finally {
        setLoading(false); // 数据加载结束
      }
    };

    fetchData();
  }, [num, specificTopics, mode]);

  // ECharts 图表配置
  const options = {
    title: { text: mode === 'top' ? `Top ${num} Java Topics` : `Specific Java Topics` },
    tooltip: {
      trigger: 'axis',
      axisPointer: { type: 'shadow' }, // 鼠标悬停时高亮
    },
    xAxis: {
      type: 'category',
      data: data.map((item) => item.topic),
      axisLabel: {
        interval: 0, // 显示所有标签
        rotate: 45, // 旋转45度以避免重叠
        formatter: (value) => value.length > 10 ? `${value.slice(0, 10)}...` : value, // 超长截断
      },
    },
    yAxis: { type: 'value', name: 'Count' },
    dataZoom: [
      {
        type: 'slider', // 启用缩放条
        show: true,
        xAxisIndex: 0, // 绑定到 x 轴
        start: 0, // 默认展示数据的起始百分比
        end: num <= 10 ? 100 : 50, // 根据 n 调整默认缩放范围
      },
      {
        type: 'inside', // 支持鼠标滚轮缩放
        xAxisIndex: 0,
        start: 0,
        end: num <= 10 ? 100 : 50,
      },
    ],
    series: [
      {
        type: 'bar',
        data: data.map((item) => item.count),
        itemStyle: { color: '#1E90FF' }, // 自定义颜色
      },
    ],
  };

  return (
    <div style={{ padding: '20px' }}>
      <h2>Java Topics Analysis</h2>
      
      {/* 模式选择 */}
      <div style={{ marginBottom: '20px' }}>
        <button
          onClick={() => setMode('top')}
          style={{
            marginRight: '10px',
            padding: '10px 15px',
            backgroundColor: mode === 'top' ? '#4CAF50' : '#f1f1f1',
            color: mode === 'top' ? '#fff' : '#000',
            border: 'none',
            borderRadius: '5px',
            cursor: 'pointer',
          }}
        >
          Top Topics
        </button>
        <button
          onClick={() => setMode('specific')}
          style={{
            padding: '10px 15px',
            backgroundColor: mode === 'specific' ? '#4CAF50' : '#f1f1f1',
            color: mode === 'specific' ? '#fff' : '#000',
            border: 'none',
            borderRadius: '5px',
            cursor: 'pointer',
          }}
        >
          Specific Topics
        </button>
      </div>

      {/* 根据模式动态渲染 */}
      {mode === 'top' ? (
        <div>
          <label htmlFor="num-select">Select N:</label>
          <select
            id="num-select"
            value={num}
            onChange={(e) => setNum(Number(e.target.value))}
            style={{
              marginLeft: '10px',
              padding: '5px',
              fontSize: '14px',
              border: '1px solid #ddd',
              borderRadius: '5px',
            }}
          >
            <option value={5}>5</option>
            <option value={10}>10</option>
            <option value={15}>15</option>
            <option value={20}>20</option>
            <option value={30}>30</option>
            <option value={50}>50</option>
          </select>
        </div>
      ) : (
        <div>
          <label htmlFor="specific-topics">Enter Topics (comma separated):</label>
          <input
            id="specific-topics"
            type="text"
            value={specificTopics}
            onChange={(e) => setSpecificTopics(e.target.value)}
            placeholder="e.g., Stream API,NullPointerException"
            style={{
              marginLeft: '10px',
              width: '300px',
              padding: '5px',
              fontSize: '14px',
              border: '1px solid #ddd',
              borderRadius: '5px',
            }}
          />
        </div>
      )}

      {/* 加载中状态 */}
      {loading ? (
        <p>Loading...</p>
      ) : (
        <div>
          {/* 图表展示 */}
          <ReactECharts option={options} style={{ height: '400px', marginTop: '20px' }} />

          {/* 表格展示 */}
          <table
            border="1"
            cellPadding="8"
            style={{
              marginTop: '20px',
              width: '100%',
              textAlign: 'center',
              borderCollapse: 'collapse',
              fontSize: '14px',
            }}
          >
            <thead>
              <tr style={{ backgroundColor: '#f5f5f5' }}>
                <th>#</th>
                <th>Topic</th>
                <th>Count</th>
              </tr>
            </thead>
            <tbody>
              {data.length > 0 ? (
                data.map((item, index) => (
                  <tr key={index}>
                    <td>{index + 1}</td>
                    <td title={item.topic}>{item.topic || 'N/A'}</td>
                    <td>{item.count || 0}</td>
                  </tr>
                ))
              ) : (
                <tr>
                  <td colSpan="3">No data available</td>
                </tr>
              )}
            </tbody>
          </table>
        </div>
      )}
    </div>
  );
};

export default JavaTopics;
