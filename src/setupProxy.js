const { createProxyMiddleware } = require('http-proxy-middleware');

module.exports = function (app) {
    app.use(
        '/Project-Final', // 匹配所有以 /Project-Final 开头的请求
        createProxyMiddleware({
            target: 'http://10.16.59.146:13456', // 后端服务器地址
            changeOrigin: true,
        })
    );
};
