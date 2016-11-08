
def openSearchFromStartScreen():
	wait(1000)
	tap.resourceId("org.wikipedia.alpha:id/search_container", area="org.wikipedia.alpha:id/feed_app_bar_layout", wait=30000, scroll=False)
	
def searchAndOpenArticleWithName(articleName):
	verify.resourceId('org.wikipedia.alpha:id/search_src_text', wait=30000)
	input.text(articleName)
	tap.text(articleName, resourceId="org.wikipedia.alpha:id/page_list_item_title", sync = True, wait=30000)
	
def assertArticleTitleContains(articleName):
	titleView = get.item.resourceId("org.wikipedia.alpha:id/view_article_header_text", wait=30000)
	assert articleName in titleView.Text, articleName + " not in title"
	
	
def openSearchFromArticle():
	tap.resourceId("org.wikipedia.alpha:id/main_search_bar_text")
	
def openToC():
	swipe.location((0.99, 0.5)).to.location((0.5, 0.5))
	
def closeToC():
	swipe.location((0.5, 0.5)).to.location((0.99, 0.5))
	
def openDrawer():
	swipe.location((0, 0.5)).to.location((0.5, 0.5))