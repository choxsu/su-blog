#sql("paginate")
  SELECT * FROM blog
  #for(r : cond)
    #(for.index == 0 ? "where" : "and ") #(r.key)  #para(r.value)
  #end
#end