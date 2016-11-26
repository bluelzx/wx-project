var reqUrl = '';
var reqType = "POST";
var bodyLoc = '#listBody';
var tmpLoc = '#tempBody';
/**
 * 分页工具
 * @type {{loadData: pager.loadData}}
 */
var pager = {
    /**
     * 初始化数据
     * @param setParam 设置请求参数方法
     * @param url 请求数据地址
     * @param type 请求类型, get,post
     * @param loc table锚点
     */
    initPager: function (url, type, loc) {
        if (url != '' && url != undefined && url != null) {
            reqUrl = url;
        }

        if (type != '' && type != undefined && type != null) {
            reqType = type;
        }
        if (loc != '' && loc != undefined && loc != null) {
            bodyLoc = loc;
        }
        $('#btnLeft').click(function () {
            var index = parseInt($('#txtPageIndex').val());
            if (index == 1) {
                $(this).addClass('pager-ctl');
                $('#btnBackward').addClass('pager-ctl');
                return;
            } else {
                $('#txtPageIndex').val(index - 1);
                $(this).removeClass('pager-ctl');
                $('#btnBackward').removeClass('pager-ctl');
                pager.loadData();
            }
        });
        $('#btnBackward').click(function () {
            var index = parseInt($('#txtPageIndex').val());
            if (index == 1) {
                $(this).addClass('pager-ctl');
                $('#btnLeft').addClass('pager-ctl');
                return;
            } else {
                $('#txtPageIndex').val(1);
                $(this).addClass('pager-ctl');
                $('#btnLeft').addClass('pager-ctl');
                pager.loadData();
            }
        });
        $('#btnRight').click(function () {
            var index = parseInt($('#txtPageIndex').val());
            var total = parseInt($('#txtPageIndex').attr('total'));
            if (index + 1 > total) {
                $(this).addClass('pager-ctl');
                $('#btnForward').addClass('pager-ctl');
                return;
            } else {
                $('#txtPageIndex').val(index + 1);
                $(this).removeClass('pager-ctl');
                $('#btnForward').removeClass('pager-ctl');
                pager.loadData();
            }
        });
        $('#btnForward').click(function () {
            var index = parseInt($('#txtPageIndex').val());
            var total = parseInt($('#txtPageIndex').attr('total'));
            if (index + 1 > total) {
                $(this).addClass('pager-ctl');
                $('#btnRight').addClass('pager-ctl');
                return;
            } else {
                $('#txtPageIndex').val(total);
                $(this).addClass('pager-ctl');
                $('#btnRight').addClass('pager-ctl');
                pager.loadData();
            }
        });
    },
    loadData: function (loc, callback) {
        this.setPagerState();
        $.showLoading('正在加载...');
        var index = $('#txtPageIndex').val();
        if (index < 0) {
            $('#txtPageIndex').val(1);
            return;
        }
        var pageSize = $('#txtPageSize').val();
        if (pageSize == '' || pageSize == null || pageSize == undefined) {
            pageSize = 10;
        } else {
            pageSize = parseInt($('#txtPageSize').val())
        }
        var data = {
            pageIndex: index,
            pageSize: pageSize,
            domain: $.cookie('d'),
            args: JSON.stringify(getParam())
        };

        $.ajax({
            url: reqUrl,
            type: reqType,
            data: data,
            dataType: 'json',
            success: function (resp) {
                $.hideLoading(100);
                var totalCount = parseInt(resp.total);
                var pageTotal = parseInt(totalCount % pageSize == 0
                    ? totalCount / pageSize
                    : (totalCount / pageSize + 1));
                pager.setPagerState(pageTotal);
                $('#txtPageIndex').attr('total', pageTotal);
                $('#pageInfo').text(index + '/' + pageTotal);
                $(bodyLoc).empty();
                if (!loc) {
                    loc = tmpLoc;
                }
                if (resp.data) {
                    $(loc).tmpl(resp.data).appendTo(bodyLoc);
                } else if (resp.list) {
                    $(loc).tmpl(resp.list).appendTo(bodyLoc);
                }

                $('.chkId').click(function () {
                    var checked = $(this).is(':checked');
                    if (checked) {
                        $(this).parent().parent().css('background-color', '#ebf3fd');
                    } else {
                        $(this).parent().parent().css('background-color', 'transparent');
                    }
                });

                if (callback) {
                    callback();
                }
            },
            error: function (resp) {
                $.hideLoading(100);
                $.showToast('系统出错，请稍后再试');
            }
        });
    },
    setPagerState: function (total) {
        var index = parseInt($('#txtPageIndex').val());
        if (index == 1) {
            $('#btnLeft').addClass('pager-ctl');
            $('#btnBackward').addClass('pager-ctl');
        } else {
            $('#btnLeft').removeClass('pager-ctl');
            $('#btnBackward').removeClass('pager-ctl');
        }
        if (index + 1 > total) {
            $('#btnForward').addClass('pager-ctl');
            $('#btnRight').addClass('pager-ctl');
        } else {
            $('#btnForward').removeClass('pager-ctl');
            $('#btnRight').removeClass('pager-ctl');
        }
    }
};
