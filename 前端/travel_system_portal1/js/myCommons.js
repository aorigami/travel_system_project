//js公共抽取方法
const comm = {
    //上一页，下一页，首页、尾页，自选也
    upOrNextOrLastOrFirstPage(url, page, rows) {
        axios.get(url + "?page=" + page + "&rows=" + rows).then(res => {
            this.setPageInfo(res.data);
        }).catch(error => {
            alert("error！服务器内部错误！");
        })
    },

    //初始加载页面
    findAll(url) {
        axios.get(url).then(res => {
            if (res.status == 200) {
                this.pageInfo = res.data;
            }
        }).catch(function (error) {
            alert("error！服务器内部错误！");
        })
    },
    //设置页面显示几条数据
    setPageRows(url) {
        var rows = $("#setPageRows").val();
        axios.get(url + "?rows=" + rows).then(res => {
            this.pageInfo = res.data;
        }).catch(error => {
            alert("error！服务器内部错误！");
        })
    }
}