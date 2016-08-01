
def openSearchFromStartScreen():
	tap.resourceId("org.wikipedia.alpha:id/search_container", scroll = False)
	
def searchAndOpenArticleWithName(articleName):
	input.text(articleName)
	tap.text(articleName, resourceId="org.wikipedia.alpha:id/page_list_item_title", sync = True)
	
def assertArticleTitleContains(articleName):
	titleView = get.item.resourceId("org.wikipedia.alpha:id/view_article_header_text")
	assert articleName in titleView.Text, articleName + " not in title"
	
	
def openSearchFromArticle():
	tap.resourceId("org.wikipedia.alpha:id/main_search_bar_text")
	
def openToC():
	swipe.location((0.99, 0.5)).to.location((0.5, 0.5))
	
def closeToC():
	swipe.location((0.5, 0.5)).to.location((0.99, 0.5))
	
def openDrawer():
	swipe.location((0, 0.5)).to.location((0.5, 0.5))