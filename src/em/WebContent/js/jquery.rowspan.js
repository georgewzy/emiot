/**
 * 用于合并单元格（行）,
 * 参数：
 *       colIdx: 要合并的列号
 *       maxRowsColIdx: 行的最大合并深度参照列
 */
jQuery.fn.rowspan = function(colIdx, maxRowsColIdx) {
    return this.each(function() {
        var that, maxRow
        
        $('tr', this).each(function(row) {
            if (typeof maxRowsColIdx != 'undefined' && maxRow == null) {
                var $maxRowTd = $('td:eq('+ maxRowsColIdx +')', this)
                
                maxRow = $maxRowTd.attr('rowSpan')
                if (maxRow == undefined) maxRow = 1
            }
            
            $('td:eq('+ colIdx +')', this).filter(':visible').each(function(col) {
                if (that != null && $(this).html() == $(that).html()) {
                    rowspan = $(that).attr('rowSpan')
                    if (rowspan == undefined) {
                        $(that).attr('rowSpan', 1)
                        rowspan = $(that).attr('rowSpan')
                    }
                    rowspan = Number(rowspan) + 1
                    $(that).attr('rowSpan', rowspan)
                    $(this).hide()
                } else {
                    that = this
                } 
            })
            
            if (maxRow != null) {
                maxRow --
                if (!maxRow) {
                    maxRow = null
                    that   = null
                }
            }
        })
    })
}