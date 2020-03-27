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
        path: path.resolve(__dirname, "dist"),
    },
    resolve: {
        extensions: ['.js']
    },
    plugins: [
        new HtmlWebpackPlugin({
            template: '.\\dist\\index.html'
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
        // {
        //     test: /\.worker\.js$/,
        //     use: {
        //         loader: 'worker-loader',
        //         options: {
        //             inline: true,
        //             fallback: false,
        //             name: "worker.[hash].js"
        //         }
        //     }
        // },
        // {
        //     // Styles
        //     test: /\.css$/,
        //     use: [
        //         'style-loader',
        //         'css-loader'
        //     ]
        // },
        // {
        //     test: /\.(scss)$/,
        //     use: [{
        //         loader: 'style-loader', // inject CSS to page
        //     }, {
        //         loader: 'css-loader', // translates CSS into CommonJS modules
        //     }, {
        //         loader: 'postcss-loader', // Run post css actions
        //         options: {
        //             plugins: function () { // post css plugins, can be exported to postcss.config.js
        //                 return [
        //                     require('precss'),
        //                     require('autoprefixer')
        //                 ];
        //             }
        //         }
        //     }, {
        //         loader: 'sass-loader' // compiles Sass to CSS
        //     }]
        // },
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