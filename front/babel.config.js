module.exports = {
    presets: [
        '@vue/app'
    ],
    "plugins": [
        "transform-vue-jsx",
        "transform-runtime",
        ["import", {"libraryName": "ant-design-vue", "libraryDirectory": "es", "style": "css"}]
    ]
}
