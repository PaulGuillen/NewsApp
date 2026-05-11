//package com.devpaul.infoxperu.feature.home.home_view.uc
//
//import com.devpaul.infoxperu.core.extension.ResultState
//import com.devpaul.infoxperu.core.urls.ApiService
//import com.devpaul.infoxperu.core.urls.ApiServiceGoogleNews
//import com.devpaul.infoxperu.domain.models.res.Article
//import com.devpaul.infoxperu.domain.models.res.GDELProject
//import com.devpaul.infoxperu.feature.home.home_view.repository.NewsRepository
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.ExperimentalCoroutinesApi
//import kotlinx.coroutines.test.UnconfinedTestDispatcher
//import kotlinx.coroutines.test.advanceUntilIdle
//import kotlinx.coroutines.test.resetMain
//import kotlinx.coroutines.test.runTest
//import kotlinx.coroutines.test.setMain
//import org.junit.After
//import org.junit.Before
//import org.junit.Test
//import org.mockito.InjectMocks
//import org.mockito.Mock
//import org.mockito.Mockito.mock
//import org.mockito.Mockito.`when`
//import org.mockito.MockitoAnnotations
//import retrofit2.Call
//import retrofit2.Response
//import kotlin.test.assertEquals
//import kotlin.test.assertTrue
//
//@ExperimentalCoroutinesApi
//class GDELTUseCaseTest {
//
//    @Mock
//    private lateinit var apiService: ApiService
//
//    @Mock
//    private lateinit var apiServiceGoogleNews: ApiServiceGoogleNews
//
//    @InjectMocks
//    private lateinit var repository: NewsRepository
//
//    private lateinit var projectGDELTUseCase: GDELTUseCase
//
//    private val testDispatcher = UnconfinedTestDispatcher()
//
//    @Before
//    fun setUp() {
//        MockitoAnnotations.openMocks(this)
//        Dispatchers.setMain(testDispatcher)
//        projectGDELTUseCase = GDELTUseCase(repository)
//    }
//
//    @After
//    fun tearDown() {
//        Dispatchers.resetMain()
//    }
//
//    @Test
//    fun `invoke should return Success when valid articles are found`() = runTest {
//        val articles = listOf(
//            Article("Title1", "Image1", "Date1", "Domain1", "", "", "", ""),
//            Article("Title2", "Image2", "Date2", "Domain2", "", "", "", "")
//        )
//        val projectGDELT = GDELProject(articles)
//        val call = mock(Call::class.java) as Call<GDELProject>
//        `when`(call.execute()).thenReturn(Response.success(projectGDELT))
//        `when`(apiService.deltaProject("query", "mode", "format")).thenReturn(call)
//
//        val result = projectGDELTUseCase("query", "mode", "format")
//        advanceUntilIdle()
//        assertTrue(result is ResultState.Success)
//        assertEquals(2, result.data.articles.size)
//    }
//
//    @Test
//    fun `invoke should return Error when no valid articles are found`() = runTest {
//        val articles = listOf(
//            Article("", "", "", "", "", "", "", ""),
//            Article("", "", "", "", "", "", "", "")
//        )
//        val projectGDELT = GDELProject(articles)
//        val call = mock(Call::class.java) as Call<GDELProject>
//        `when`(call.execute()).thenReturn(Response.success(projectGDELT))
//        `when`(apiService.deltaProject("query", "mode", "format")).thenReturn(call)
//
//        val result = projectGDELTUseCase("query", "mode", "format")
//        advanceUntilIdle()
//        assertTrue(result is ResultState.Error)
//        assertEquals(
//            "No se encontraron artículos válidos.",
//            result.exception.message
//        )
//    }
//
//    @Test
//    fun `invoke should return Error when exception is thrown`() = runTest {
//        val call = mock(Call::class.java) as Call<GDELProject>
//        `when`(call.execute()).thenThrow(RuntimeException("Network error"))
//        `when`(apiService.deltaProject("query", "mode", "format")).thenReturn(call)
//
//        val result = projectGDELTUseCase("query", "mode", "format")
//        advanceUntilIdle()
//        assertTrue(result is ResultState.Error)
//        assertEquals("Network error", result.exception.message)
//    }
//
//    @Test
//    fun `invoke should return Success with limited valid articles`() = runTest {
//        val articles = List(15) { index ->
//            Article("Title$index", "Image$index", "Date$index", "Domain$index", "", "", "", "")
//        }
//        val gdelProject = GDELProject(articles)
//        val call = mock(Call::class.java) as Call<GDELProject>
//        `when`(call.execute()).thenReturn(Response.success(gdelProject))
//        `when`(apiService.deltaProject("query", "mode", "format")).thenReturn(call)
//
//        val result = projectGDELTUseCase("query", "mode", "format")
//        advanceUntilIdle()
//        assertTrue(result is ResultState.Success)
//        assertEquals(10, result.data.articles.size)
//    }
//
//    @Test
//    fun `invoke should return Success with mixed valid and invalid articles`() = runTest {
//        val articles = listOf(
//            Article("Title1", "Image1", "Date1", "Domain1", "", "", "", ""),
//            Article("", "", "", "", "", "", "", ""),
//            Article("Title2", "Image2", "Date2", "Domain2", "", "", "", ""),
//            Article("", "", "", "", "", "", "", "")
//        )
//        val gdelProject = GDELProject(articles)
//        val call = mock(Call::class.java) as Call<GDELProject>
//        `when`(call.execute()).thenReturn(Response.success(gdelProject))
//        `when`(apiService.deltaProject("query", "mode", "format")).thenReturn(call)
//
//        val result = projectGDELTUseCase("query", "mode", "format")
//        advanceUntilIdle()
//        assertTrue(result is ResultState.Success)
//        assertEquals(2, result.data.articles.size)
//    }
//
//}
