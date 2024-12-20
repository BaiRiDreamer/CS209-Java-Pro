import React, { useEffect, useState } from 'react';
import ReactECharts from 'echarts-for-react';
import { 
  fetchHighQualityAnswerLengthDistribution,
  fetchFirstAnswerAcceptedRatio,
  fetchAcceptedAnswerRatio,
  fetchHighQualityAnswerByHighReputationUserRatio
} from '../api'; // 确保 API 方法已实现

const AnswerQuality = () => {
  const [lengthDistribution, setLengthDistribution] = useState({}); // 长度范围分布数据
  const [firstAnswerAcceptedRatio, setFirstAnswerAcceptedRatio] = useState(null); // 第一个回答被采纳比例
  const [acceptedAnswerRatio, setAcceptedAnswerRatio] = useState(null); // 被采纳的回答比例
  const [highQualityByReputationRatio, setHighQualityByReputationRatio] = useState(null); // 高质量回答的高声誉用户比例
  const [voteThreshold, setVoteThreshold] = useState(10); // 投票阈值

  // 加载数据
  useEffect(() => {
    // 加载长度分布数据
    fetchHighQualityAnswerLengthDistribution(voteThreshold).then((response) => {
      setLengthDistribution(response.data || {});
    });

    // 加载三个数值数据
    fetchFirstAnswerAcceptedRatio().then((response) => setFirstAnswerAcceptedRatio(response.data));
    fetchAcceptedAnswerRatio().then((response) => setAcceptedAnswerRatio(response.data));
    fetchHighQualityAnswerByHighReputationUserRatio(voteThreshold, 100).then((response) =>
      setHighQualityByReputationRatio(response.data)
    );
  }, [voteThreshold]);

  // 配置长度分布的图表选项
  const lengthDistributionOptions = {
    title: { text: 'High-Quality Answer Length Distribution' },
    tooltip: { trigger: 'item' },
    legend: { top: '5%' },
    series: [
      {
        name: 'Length Distribution',
        type: 'pie', // 使用饼图展示
        radius: '50%',
        data: Object.entries(lengthDistribution).map(([range, value]) => ({
          name: range,
          value: value,
        })),
        emphasis: {
          itemStyle: {
            shadowBlur: 10,
            shadowOffsetX: 0,
            shadowColor: 'rgba(0, 0, 0, 0.5)',
          },
        },
      },
    ],
  };

  // 配置 First Answer 和 Accepted Answer 对比的图表选项
  const comparisonOptions = {
    title: { text: 'First vs Accepted Answer Ratios' },
    xAxis: { type: 'category', data: ['First Answer Accepted', 'Accepted Answer'] },
    yAxis: { type: 'value', name: 'Ratio' },
    tooltip: {
      trigger: 'axis',
      formatter: '{b}: {c}%',
    },
    series: [
      {
        name: 'Ratios',
        type: 'bar',
        data: [
          (firstAnswerAcceptedRatio || 0) * 100, // 转换为百分比
          (acceptedAnswerRatio || 0) * 100, // 转换为百分比
        ],
        itemStyle: {
          color: '#1E90FF', // 自定义柱状图颜色
        },
      },
    ],
  };

  return (
    <div>
      <h2>Answer Quality Analysis</h2>

      {/* 输入投票阈值 */}
      <div style={{ marginBottom: '20px' }}>
        <label htmlFor="vote-threshold">Vote Threshold:</label>
        <input
          id="vote-threshold"
          type="number"
          value={voteThreshold}
          onChange={(e) => setVoteThreshold(Number(e.target.value))}
          style={{ marginLeft: '10px', width: '80px' }}
        />
      </div>

      {/* 长度分布的饼图 */}
      <ReactECharts
        option={lengthDistributionOptions}
        style={{ height: '400px', marginTop: '20px' }}
      />

      {/* 对比柱状图 */}
      <ReactECharts
        option={comparisonOptions}
        style={{ height: '400px', marginTop: '20px' }}
      />

      {/* 显示三个数值 */}
      <div style={{ marginTop: '20px' }}>
        <h3>Key Metrics</h3>
        <table border="1" cellPadding="8" style={{ marginTop: '10px', width: '100%', textAlign: 'center' }}>
          <thead>
            <tr>
              <th>Metric</th>
              <th>Value</th>
            </tr>
          </thead>
          <tbody>
            <tr>
              <td>First Answer Accepted Ratio</td>
              <td>{firstAnswerAcceptedRatio !== null ? `${(firstAnswerAcceptedRatio * 100).toFixed(2)}%` : 'Loading...'}</td>
            </tr>
            <tr>
              <td>Accepted Answer Ratio</td>
              <td>{acceptedAnswerRatio !== null ? `${(acceptedAnswerRatio * 100).toFixed(2)}%` : 'Loading...'}</td>
            </tr>
            <tr>
              <td>High-Quality Answer by High Reputation User Ratio</td>
              <td>
                {highQualityByReputationRatio !== null
                  ? `${(highQualityByReputationRatio * 100).toFixed(2)}%`
                  : 'Loading...'}
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default AnswerQuality;
