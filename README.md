# Merry-Go-Round
------------
## 앱 개발 동기
> 공매 관련 앱 중에 건물이나 토지를 구매를 하는 경우, 주변 지형과 주변에 편의 시설등의 정보를 함께 설명해주는
앱이 없다는 생각에 공매 관련 정보와 더불어 관련 주변 정보들을 지도를 통해 나타내 줄 수 있는 앱이 있었으면 
좋겠다는 생각에 해당 앱을 개발하게 되었습니다.
------------
## 기능
> 1. 앱 내에서 검색을 통한 공매물 관련 주요 정보들과 리스트 표시
> 2. 해당 리스트 중 원하는 item을 선택해서 상세 정보 확인 가능
> 3. 버튼을 통해 해당 공매 기관에 위치를 지도에서 확인 가능
------------
## 사용 기술
> + Android Studio
> + Kotlin
> + Google Map Api
> + 이용기관공매물건조회서비스 Api
------------
## API 파싱
> 위에 코드를 통해 데이터를 파싱 받을 수 있습니다.
> 데이터를 파싱 받기 전에 받기 원하는 데이터를 받을 변수를 설정해주고 해당 데이터를 받을 수 있는 Data에 해당하는 데이터 data class를 만들어주면 됩니다.
``` Kotlin
 while (eventType != XmlPullParser.END_DOCUMENT) {

                when (eventType) {

                    XmlPullParser.START_TAG
                    -> {
                        tag = parser.name

                        if (tag == "item") {
                        } else if (tag == "PBCT_NO") {
                            parser.next()
                            pbct_no = parser.text
                        } else if (tag == "PLNM_NM") {
                            parser.next()
                            plnm_nm = parser.text
                        } else if (tag == "CTGR_FULL_NM") {
                            parser.next()
                            ctgr_full = parser.text
                        } else if (tag == "PBCT_BEGN_DTM") {
                            parser.next()
                            pbct_begn = parser.text
                            try {
                                std = stsdf.parse(pbct_begn)
                            }catch (e : Exception){
                                Log.v("Exception",e.localizedMessage)
                            }
                            stsdf.applyLocalizedPattern("yyyy년 MM월 dd일 hh:mm")
                            pbct_begn = stsdf.format(std)

                        } else if (tag == "PBCT_CLS_DTM") {
                            parser.next()
                            pbct_cls = parser.text
                            try {
                                endd = endsdf.parse(pbct_cls)
                            }catch (e:Exception){
                                Log.v("Exception",e.localizedMessage)
                            }
                            endsdf.applyLocalizedPattern("yyyy년 MM월 dd일 hh:mm")
                            pbct_cls = endsdf.format(endd)
                        } else if(tag == "PBCT_EXCT_DTM") {
                            parser.next()
                            pbct_exct_dtm = parser.text
                        } else if(tag == "PLNM_DT") {
                            parser.next()
                            plnm_dt = parser.text
                        }
                        else if(tag == "PLNM_NO") {
                            parser.next()
                            plnm_no = parser.text
                        } else if(tag == "PLNM_KIND_NM") {
                            parser.next()
                            plnm_kind_nm = parser.text
                        } else if(tag == "BID_DVSN_NM") {
                            parser.next()
                            bid_dvsn_nm = parser.text
                        } else if(tag == "ORG_NM") {
                            parser.next()
                            org_plnm_no = parser.text
                        } else if(tag == "BID_MTD_NM") {
                            parser.next()
                            bid_mtd_nm = parser.text
                        } else if(tag == "TOT_AMT_UNPC_DVSN_NM") {
                            parser.next()
                            tot_amt_unpc_dvsn_nm = parser.text
                        } else if(tag == "DPSL_MTD_NM") {
                            parser.next()
                            dpsl_mtd_nm = parser.text
                        } else if(tag == "PRPT_DVSN_NM") {
                            parser.next()
                            prpt_dvsn_nm = parser.text
                        }
                        else if (tag == "totalCount") {
                            if (btotal) {
                                parser.next()
                                val finalnum = parser.text.toInt()
                                if (finalnum == 0) {
                                    endpage = -1
                                } else if (finalnum % 10 == 0) {
                                    endpage = finalnum / 10
                                } else {
                                    endpage = finalnum / 10 + 1
                                }
                                btotal = false

                            }
                        }
                    }

                    XmlPullParser.END_TAG
                    -> if (parser.name == "item") {
                        var data = Data(pbct_no, plnm_nm, ctgr_full, pbct_begn, pbct_cls, pbct_exct_dtm, plnm_dt, plnm_no, plnm_kind_nm, bid_dvsn_nm, org_plnm_no, bid_mtd_nm, tot_amt_unpc_dvsn_nm, dpsl_mtd_nm,prpt_dvsn_nm)
                        ldata.add(data)
                    }

                }
                eventType = parser.next()
            }
```
> Api 데이터를 불러오는 과정에서 빠르게 데이터를 가져오기 위해 Thread와 Runnable을 사용하였습니다.

> inner class를 통해 Runnable 구현
``` Kotlin
inner class WorkerRunnable : Runnable{
        override fun run() {
            TrafficStats.setThreadStatsTag(Thread.currentThread().id.toInt())
            fdata = searchData()
            runOnUiThread(){
                btotal = true
                if (endpage <0) {
                    Toast.makeText(this@RecycleData, "검색 결과가 존재하지 않습니다.", Toast.LENGTH_SHORT).show()
                } else {
                    var adapter = RecyclerAdapter()
                    var data: MutableList<Data> = fdata
                    adapter.listData = data
                    recyclerView.adapter = adapter
                    recyclerView.layoutManager = LinearLayoutManager(this@RecycleData)
                }
               backBut.setOnClickListener{
                   if (count == 1) {
                       Toast.makeText(this@RecycleData, "처음 페이지 입니다.", Toast.LENGTH_SHORT).show()
                   } else {
                       count -= 1
                       var adapter = RecyclerAdapter()
                       val data: MutableList<Data> = searchData()
                       adapter.listData = data
                       recyclerView.adapter = adapter
                       recyclerView.layoutManager = LinearLayoutManager(this@RecycleData)
                   }
               }
                firstBut.setOnClickListener {
                    count = 1
                    var adapter = RecyclerAdapter()
                    val data: MutableList<Data> = searchData()
                    adapter.listData = data
                    recyclerView.adapter = adapter
                    recyclerView.layoutManager = LinearLayoutManager(this@RecycleData)
                }
                goBut.setOnClickListener {
                    if (count == endpage) {
                        Toast.makeText(this@RecycleData, "마지막 페이지 입니다.", Toast.LENGTH_SHORT).show()
                    } else {

                        count += 1
                        var adapter = RecyclerAdapter()
                        val data: MutableList<Data> = searchData()
                        adapter.listData = data
                        recyclerView.adapter = adapter
                        recyclerView.layoutManager = LinearLayoutManager(this@RecycleData)
                    }
                }
            }

        }
    }
```
 > Thread 사용
```Kotlin
var thread = Thread(WorkerRunnable())
            thread.start()
```
------------
## Screen_Shots
<div>
<img width="200" src="![KakaoTalk_20200828_232319976_01](https://user-images.githubusercontent.com/67555400/91582657-1200f500-e98b-11ea-8fb8-78e5a92ca1f2.jpg)">
<img width="200" src="![KakaoTalk_20200828_232319976_02](https://user-images.githubusercontent.com/67555400/91582720-280eb580-e98b-11ea-8b43-1859a91108e2.jpg)">
</div>
<div>
<img width="200" src="![KakaoTalk_20200828_232319976_03](https://user-images.githubusercontent.com/67555400/91582744-3066f080-e98b-11ea-90ee-703fcb434923.jpg)">
<img width="200" src="![KakaoTalk_20200828_232319976_04](https://user-images.githubusercontent.com/67555400/91582771-3a88ef00-e98b-11ea-8dd8-d4acd0ae22e3.jpg)">
</div>





