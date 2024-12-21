import React, { useEffect, useState } from 'react';
import ReactECharts from 'echarts-for-react';
import { fetchTopUserEngagement } from '../api'; // 确保此 API 方法已经实现

const UserEngagement = () => {
  const [data, setData] = useState([]);
  const [n, setN] = useState(6); // 默认展示前 5 个条目
  const [reputationLimit, setReputationLimit] = useState(100); // 声誉限制（可调整）

  useEffect(() => {
    // 根据用户选择的 N 和声誉限制，调用 API 获取数据
    const fetchData = async () => {
      try {
        const response = await fetchTopUserEngagement(reputationLimit, n);
        const filteredData = Array.isArray(response.data)
          ? response.data.filter((_, index) => index > 0).slice(0, n) // 去掉第一条，并限制条数为 n
          : [];
        setData(filteredData);
      } catch (error) {
        console.error("Error fetching data:", error);
        setData([]);
      }
    };

    fetchData();
  }, [n, reputationLimit]);

  // 配置 ECharts 图表选项
  const options = {
    title: { text: `Top ${n - 1} Topics by User Engagement` },
    tooltip: {
      trigger: 'axis',
      axisPointer: { type: 'shadow' }, // 鼠标悬停时显示高亮
    },
    xAxis: {
      type: 'category',
      data: data.map((item) => item.topic), // 横轴显示话题名
      axisLabel: {
        interval: 0, // 显示所有标签
        rotate: 45, // 标签旋转 45 度，避免重叠
        formatter: (value) => value.length > 10 ? `${value.slice(0, 10)}...` : value, // 超长标签截断
      },
    },
    yAxis: {
      type: 'value',
      name: 'Participation Count', // 纵轴显示参与度计数
    },
    dataZoom: [
      {
        type: 'slider', // 启用缩放条
        show: true,
        xAxisIndex: 0, // 绑定到 x 轴
        start: 0,
        end: n <= 10 ? 100 : 50, // 根据 N 的大小动态调整默认范围
      },
      {
        type: 'inside', // 支持鼠标滚轮缩放
        xAxisIndex: 0,
        start: 0,
        end: n <= 10 ? 100 : 50,
      },
    ],
    series: [
      {
        type: 'bar',
        data: data.map((item) => item.participation_count), // 数据源
        itemStyle: {
          color: '#1E90FF', // 自定义柱状图颜色（道奇蓝）
        },
      },
    ],
  };

  return (
    <div style={{ padding: '20px' }}>
      <h2>User Engagement Analysis</h2>
      
      {/* 下拉框：选择显示的 N 个条目 */}
      <label htmlFor="n-select">Select N:</label>
      <select
        id="n-select"
        value={n}
        onChange={(e) => setN(Number(e.target.value))}
        style={{ marginLeft: '10px', padding: '5px', fontSize: '14px' }}
      >
        <option value={6}>5</option>
        <option value={11}>10</option>
        <option value={16}>15</option>
        <option value={21}>20</option>
        <option value={31}>30</option>
        <option value={51}>50</option>
      </select>
      
      {/* 输入框：设置声誉限制 */}
      <label htmlFor="reputation-select" style={{ marginLeft: '10px' }}>Reputation Limit:</label>
      <input
        id="reputation-select"
        type="number"
        value={reputationLimit}
        onChange={(e) => setReputationLimit(Number(e.target.value))}
        style={{
          width: '100px',
          marginLeft: '10px',
          padding: '5px',
          fontSize: '14px',
          border: '1px solid #ddd',
          borderRadius: '5px',
        }}
      />
      
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
            <th>Participation Count</th>
          </tr>
        </thead>
        <tbody>
          {data.length > 0 ? (
            data.map((item, index) => (
              <tr key={index}>
                <td>{index + 1}</td>
                <td
                  title={item.topic} // 鼠标悬停显示完整话题
                  style={{ textOverflow: 'ellipsis', overflow: 'hidden', whiteSpace: 'nowrap' }}
                >
                  {item.topic}
                </td>
                <td>{item.participation_count}</td>
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
  );
};

export default UserEngagement;
