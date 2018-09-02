defmodule HttpApiTesterTest do
  use ExUnit.Case
  doctest HttpApiTester

  test "sends requests at the API" do
    # todo call API tester to load orders

    {:ok, response} = HTTPoison.get("http://localhost:8081/api/statistics", [], [])

    IO.puts response.body

    assert response.status_code == 200
  end
end
