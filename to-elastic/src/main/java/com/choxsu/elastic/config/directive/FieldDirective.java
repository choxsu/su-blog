package com.choxsu.elastic.config.directive;


import com.jfinal.template.Directive;
import com.jfinal.template.Env;
import com.jfinal.template.io.Writer;
import com.jfinal.template.stat.CharTable;
import com.jfinal.template.stat.Scope;

/**
 * @author chox su
 * @date 2017/12/07 14:48
 */
public class FieldDirective extends Directive {

    @Override
    public void exec(Env env, Scope scope, Writer writer) {
        String key = exprList.eval(scope).toString().trim();
        write(writer, key);
        if (CharTable.isLetterOrDigit(key.charAt(key.length() - 1))) {
            write(writer, " = ");
        }
    }
}
