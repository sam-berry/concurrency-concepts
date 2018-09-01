defmodule HttpApiTesterTest do
  use ExUnit.Case
  doctest HttpApiTester

  defp postOrder() do
    HTTPoison.post "http://localhost:8081/api/orders", "{\"timestamp\": \"2018-08-23T07:30:03.000Z\", \"amount\": \"100.00\"}", [{"Content-Type", "application/json"}]
  end

  test "sends a lot of requests at the API" do
    postOrder()
    postOrder()
    postOrder()
    postOrder()
    {:ok, response} = HTTPoison.get("http://localhost:8081/api/statistics", [], [])

    IO.puts response.body

    assert response.status_code == 200
  end
end
