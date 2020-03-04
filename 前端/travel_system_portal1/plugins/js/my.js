//改变每一页的数量
function setPageSize() {
    var size = $("#setPageSize").val();
    location.href = "findAll.do?page=1&size=" + size;
}
