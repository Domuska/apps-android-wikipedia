
def openSearchFromStartScreen():
	tap.resourceId("org.wikipedia.alpha:id/search_container")
	
def searchAndOpenArticleWithName(articleName):
	log("searching article with name " + articleName)
	input.text(articleName)
	log("opening the article")
	tap.text(articleName, resourceId="org.wikipedia.alpha:id/page_list_item_title")
	
def assertArticleTitleContains(articleName):
	titleView = get.item.resourceId("org.wikipedia.alpha:id/view_article_header_text")
	assert articleName in titleView.Text, articleName + " not in title"
	
	
def openSearchFromArticle():
	log("opening search from article")
	tap.resourceId("org.wikipedia.alpha:id/main_search_bar_text")
	
