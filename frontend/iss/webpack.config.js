const path = require('path');
const wpack = require('webpack');
const HtmlWebpackPlugin = require('html-webpack-plugin');
const WriteFilePlugin = require('write-file-webpack-plugin');

let entry = '.\\src\\index.js';
let watch = false;

let webpackConfig = {
    mode: 'development',
    watch: watch,
    entry: {
        index: entry,
    },
    output: {
        filename: '[name].js',
        path: path.resolve(__dirname, ""),
    },
    resolve: {
        extensions: ['.js', '.ts', '.css']
    },
    plugins: [
        new HtmlWebpackPlugin({
            template: '.\\index.html'
        }),
        new WriteFilePlugin(),
        new wpack.ProvidePlugin({
            THREE: 'three'
        })
    ],
    devtool: 'eval',
    devServer: {
        port: 5500
    },
    optimization: {
        namedChunks: true
    },
    module: {
        rules: [{
            test: /\.js$/,
            use: {
                loader: 'babel-loader',
                options: {
                    presets: ['@babel/preset-env']
                }
            }
        },
        
        {
            // Graphics
            test: /\.(png|jpg|jpeg|gif|bmp|svg)/,
            use: [{
                loader: 'file-loader',
                options: {
                    name: '[path][name].[ext]',
                    context: 'src'
                }
            }]
        },
        {
            test: /\.html$/,
            use: [{
                loader: 'html-loader',
                options: {}
            }]
        }
        ]
    }
};

module.exports = webpackConfig;